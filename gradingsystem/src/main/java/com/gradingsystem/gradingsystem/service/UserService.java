package com.gradingsystem.gradingsystem.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gradingsystem.gradingsystem.exception.UsernameAlreadyExistsException;
import com.gradingsystem.gradingsystem.model.Role;
import com.gradingsystem.gradingsystem.model.User;
import com.gradingsystem.gradingsystem.repository.UserRepository;
import com.gradingsystem.gradingsystem.util.JwtUtil;

@Service
public class UserService implements UserDetailsService {

	@Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        long existingUserCount = userRepository.findByUsername(user.getUsername());
        if (existingUserCount > 0) {
            throw new UsernameAlreadyExistsException("Username already exists!");
        }
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Set default role if not specified
        if (user.getRole() == null) {
            user.setRole(Role.STUDENT);  // or any default role you prefer
        }

        userRepository.save(user);
    }
    @Autowired
    private JwtUtil jwtUtil;

    public String login(String username, String password) {
    	System.out.println("Logging in user: " + username);

        User userOptional = userRepository.findByUsernameToLogin(username);
        	
        if (userOptional != null) {
            // Check if the password matches
            if (passwordEncoder.matches(password, userOptional.getPassword())) {
            	List<String> roles = new ArrayList<>();
                roles.add(userOptional.getRole().name()); // Assuming role is an enum

                // Generate JWT token with roles
                return jwtUtil.generateToken(username, roles);
            } else {
                // Incorrect password
                throw new IllegalArgumentException("Invalid credentials: Password does not match.");
            }
        } else {
            // User not found
            throw new IllegalArgumentException("Invalid credentials: User does not exist.");
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameToLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), getAuthorities(user));
    }


    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())); // Adjust based on how you defined roles
        return authorities;
    }
}
