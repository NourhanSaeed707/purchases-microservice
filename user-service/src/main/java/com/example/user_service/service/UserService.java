package com.example.user_service.service;
import com.example.user_service.dto.UserDTO;
import com.example.user_service.model.Users;

public interface UserService {
    Users update(Long id, Users updatedUser);

    Users getOne(Long id);

    boolean delete(Long id);

    Users getUserByEmail(String email);

    UserDTO getUserById(Long id);
}
