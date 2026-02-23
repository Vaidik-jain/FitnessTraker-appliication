package com.project.fitness.service;

import com.project.fitness.Model.User;

import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse register(RegisterRequest request) {
         User user=User.builder()
                 .email(request.getEmail())
                 .password(request.getPassword())
                 .firstname(request.getFirstname())
                 .lastname(request.getLastname())
                 .password(request.getPassword())
                 .build();
        User savedUser=userRepository.save(user);
        return mapToResponse(savedUser);
    }

    private UserResponse mapToResponse(User savedUser) {
        UserResponse response= new UserResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setPassword(savedUser.getPassword());
        response.setFirstname(savedUser.getFirstname());
        response.setLastname(savedUser.getLastname());
        response.setCretedAt(savedUser.getCretedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());
        return response;
    }
}
