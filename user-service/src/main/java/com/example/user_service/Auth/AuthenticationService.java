package com.example.user_service.Auth;
import com.example.user_service.Config.JwtService;
import com.example.user_service.model.Users;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.Impl.RateLimiterService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RateLimiterService rateLimiterService;

    public AuthenticationResponse register(RegisterRequest request, HttpServletResponse response) {
        var user = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .address(request.getAddress())
                .password(request.getPassword())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        ResponseCookie cookie = ResponseCookie.from("token", jwtToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(86400)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return AuthenticationResponse.builder().token(jwtToken).status(200).build();
    }

    public AuthenticationResponse login(AuthenticationRequest request, HttpServletResponse response) {
        if(rateLimiterService.isRateLimited()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return AuthenticationResponse.builder().message("Too many login requests").status(429).build();
        }
        try {

        }
    }
}
