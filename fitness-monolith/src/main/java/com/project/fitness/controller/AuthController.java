package com.project.fitness.controller;

import com.project.fitness.Model.Activity;
import com.project.fitness.Model.User;
import com.project.fitness.dto.ActivityRequest;
import com.project.fitness.dto.ActivityResponse;
import com.project.fitness.dto.LoginRequest;
import com.project.fitness.dto.LoginResponse;
import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.repository.UserRepository;
import com.project.fitness.security.jwtUtils;
import com.project.fitness.service.ActivityService;
import com.project.fitness.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")  
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;
 
    private final jwtUtils jwtutils;



    // public AuthController(UserService userService) {
    //     this.userService = userService;

    // }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        // this is authentication process
        User user= userService.authenticate(loginRequest);
       try {
          
          
            String token= jwtutils.generateToken( user.getId(),
        user.getRole().name());
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUser(userService.mapToResponse(user));

        return ResponseEntity.ok(response);

       } catch (Exception e) {
         e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
    }
}
