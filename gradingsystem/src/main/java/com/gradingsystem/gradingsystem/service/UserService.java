package com.gradingsystem.gradingsystem.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gradingsystem.gradingsystem.exception.InvalidPasswordException;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        if (user.getRole() == null) {
            user.setRole(Role.STUDENT);
        }

        userRepository.save(user);
    }
    @Autowired
    private JwtUtil jwtUtil;
    
    
    public Map<String, Object> login(String username, String password) {
        System.out.println("Logging in user: " + username);

        User userOptional = userRepository.findByUsernameToLogin(username);

        if (userOptional != null) {
            if (passwordEncoder.matches(password, userOptional.getPassword())) {
                List<String> roles = new ArrayList<>();
                roles.add(userOptional.getRole().name());
                String token = jwtUtil.generateToken(username, roles);

                Map<String, Object> response = new HashMap<>();
                response.put("username", username);
                response.put("role", roles);
                response.put("token", token);

                return response;
            } else {
                throw new InvalidPasswordException("Invalid Credentials");
            }
        } else {
            throw new UsernameNotFoundException("User not found");
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
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())); 
        return authorities;
    }
}
