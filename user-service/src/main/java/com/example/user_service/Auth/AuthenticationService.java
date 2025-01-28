package com.example.user_service.Auth;
import com.example.user_service.Config.JwtService;
import com.example.user_service.model.Role;
import com.example.user_service.model.Users;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.Impl.RateLimiterService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
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
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
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
        return AuthenticationResponse.builder().token(jwtToken).status(200).message("register successful").build();
    }

    public AuthenticationResponse login(AuthenticationRequest request, HttpServletResponse response) {
        if (rateLimiterService.isRateLimited()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return AuthenticationResponse.builder().message("Too many login requests").status(429).build();
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            var jwtToken = jwtService.generateToken(user);
            ResponseCookie cookie = ResponseCookie.from("token", jwtToken)
                    .httpOnly(true)
                    .secure(false) // Set to true in production
                    .path("/")
                    .maxAge(86400) // 1 day
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return AuthenticationResponse.builder().token(jwtToken).status(200).message("login successful").build();
        } catch (BadCredentialsException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return AuthenticationResponse.builder().message("Invalid email or password").status(401).build();
        } catch (UsernameNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return AuthenticationResponse.builder().status(404).message("User not found").build();
        }

    }
}
