package com.gradingsystem.gradingsystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gradingsystem.gradingsystem.dto.LoginRequest;
import com.gradingsystem.gradingsystem.model.User;
import com.gradingsystem.gradingsystem.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")  
    public ResponseEntity<String> registerUser(@RequestBody User user) {
    	 userService.registerUser(user); 
         return ResponseEntity.status(201).body("User registered successfully!");
    }
    @PostMapping("/login")
    @CrossOrigin(origins = "*", allowedHeaders ="*")
    public ResponseEntity<Map<String,Object>> login(@RequestBody LoginRequest loginRequest) {
        var res = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(res);
    }
}
