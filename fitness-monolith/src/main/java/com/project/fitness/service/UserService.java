package com.project.fitness.service;

import com.project.fitness.Model.User;
import com.project.fitness.Model.UserRole;
import com.project.fitness.dto.LoginRequest;
import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

   

    public UserResponse register(RegisterRequest request) {
        UserRole role=request.getRole() != null ?request.getRole()
        : UserRole.USER;
         User user=User.builder()
                 .email(request.getEmail())
                 .password(request.getPassword())
                 .firstname(request.getFirstname())
                 .lastname(request.getLastname())
                 .password(passwordEncoder.encode(request.getPassword()))
                 .role(role)
                 .build();
        User savedUser=userRepository.save(user);
        return mapToResponse(savedUser);
    }

    public UserResponse mapToResponse(User savedUser) {
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

    public User authenticate(LoginRequest loginRequest) {
        User user= userRepository.findByEmail(loginRequest.getEmail());
        if(user==null){
            throw new RuntimeException("invalid credentials");
          }
          if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("invalid credentials");
          }
          return user;
    }
}
