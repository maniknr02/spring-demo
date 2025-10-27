package com.example.myfirstapp.service;

import com.example.myfirstapp.dto.UserRequest;
import com.example.myfirstapp.dto.UserResponse;
import com.example.myfirstapp.model.User;
import com.example.myfirstapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        // 1. Check if email already exists
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // 2. Convert UserRequest to User entity
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        // Note: createdAt/updatedAt are automatically set by @PrePersist

        // 3. Save to database
        User savedUser = userRepository.save(user);

        // 4. Convert to UserResponse and return
        return convertToResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return convertToResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Convert List<User> to List<UserResponse> using stream
        return users.stream()
                .map(this::convertToResponse)  // Method reference to helper
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        // 1. Find existing user
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // 2. Update fields from userRequest
        existingUser.setName(userRequest.getName());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPassword(userRequest.getPassword());
        // Note: updatedAt is automatically set by @PreUpdate

        // 3. Save updated user
        User updatedUser = userRepository.save(existingUser);

        // 4. Convert to UserResponse and return
        return convertToResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        // 1. Check if user exists
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // 2. Delete the user
        userRepository.delete(user);
    }
    // Helper method to convert User entity to UserResponse
    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}