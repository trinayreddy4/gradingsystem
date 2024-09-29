package com.gradingsystem.gradingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gradingsystem.gradingsystem.dto.LoginRequest;
import com.gradingsystem.gradingsystem.model.User;
import com.gradingsystem.gradingsystem.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    // Registration endpoint
    @Autowired
    private UserService userService;

    @PostMapping("/register")  // This maps POST requests to /auth/register
    public ResponseEntity<String> registerUser(@RequestBody User user) {
    	 userService.registerUser(user); // No need for try-catch; handled globally
         return ResponseEntity.status(201).body("User registered successfully!");
    }

    // Login endpoint (for demonstration purposes)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
//    	System.out.println(loginRequest.getPassword());
        String message = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(message);
    }
}
