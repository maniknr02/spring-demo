package com.example.myfirstapp.service;

import com.example.myfirstapp.dto.UserRequest;
import com.example.myfirstapp.dto.UserResponse;
import java.util.List;

public interface UserService {

    // Create a new user
    UserResponse createUser(UserRequest userRequest);

    // Get user by ID
    UserResponse getUserById(Long id);

    // Get all users
    List<UserResponse> getAllUsers();

    // Update user
    UserResponse updateUser(Long id, UserRequest userRequest);

    // Delete user
    void deleteUser(Long id);
}