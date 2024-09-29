package com.gradingsystem.gradingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gradingsystem.gradingsystem.exception.UsernameAlreadyExistsException;
import com.gradingsystem.gradingsystem.model.Role;
import com.gradingsystem.gradingsystem.model.User;
import com.gradingsystem.gradingsystem.repository.UserRepository;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
    	long existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser>0) {
            throw new UsernameAlreadyExistsException("Username already exists!");
        }
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRole()!=null)
        {
        	user.setRole(Role.TEACHER);
        }
        userRepository.save(user);
    }
    
    public String login(String username, String password) {
        // Find the user by username
//    	System.out.print(username);
//    	String passworde = passwordEncoder. 
        User userOptional = userRepository.findByUsernameToLogin(username);
        System.out.println(userOptional);
        if (userOptional!=null) {
            User user = userOptional;
            // Check if the password matches
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Successful login
                return "Login successful! Welcome, " + user.getUsername();
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
    		return null;
    }

//    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
//        return authorities;
//    }
}
