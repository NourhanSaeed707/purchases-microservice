package com.example.user_service.service.Impl;
import com.example.user_service.dto.UserDTO;
import com.example.user_service.model.Users;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Users update(Long id, Users updatedUser) {
        Users userExists = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found with id " + id));
        if(userExists != null) {
            userExists.setFirstName(updatedUser.getFirstName());
            userExists.setLastName(updatedUser.getLastName());
            userExists.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            userExists.setEmail(updatedUser.getEmail());
            userExists.setAddress(updatedUser.getAddress());
            userExists.setMobile(updatedUser.getMobile());
            userExists.setRole(updatedUser.getRole());
            userRepository.save(userExists);
        }
        return userExists;
    }

    @Override
    public Users getOne(Long id) {
        Users userExist = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        return userExist;
    }

    @Override
    public boolean delete(Long id) {
        Users userExist = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        userRepository.delete(userExist);
        if(userExist != null) {
            return true;
        }
        return false;
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("user not found with email " + email));
    }

    @Override
    public UserDTO getUserById(Long id) {
        Users user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found with id " + id));
        return UserDTO.builder().
                firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
