package com.gradingsystem.gradingsystem.filter;

import com.gradingsystem.gradingsystem.service.UserService;
import com.gradingsystem.gradingsystem.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	 private final JwtUtil jwtUtil;
	    private final UserService userService;

	    @Autowired
	    public JwtRequestFilter(JwtUtil jwtUtil, UserService userService) {
	        this.jwtUtil = jwtUtil;
	        this.userService = userService;
	    }


	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	            throws ServletException, IOException {
	        
	        final String authorizationHeader = request.getHeader("Authorization");

	        String username = null;
	        String jwt = null;

	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            jwt = authorizationHeader.substring(7); // Extract token from "Bearer <token>"
	            username = jwtUtil.extractUsername(jwt); // Extract username from the token
	        }

	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = userService.loadUserByUsername(username); // Load user details
	            if (jwtUtil.validateToken(jwt, userDetails)) {
	                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                    userDetails, null, userDetails.getAuthorities());
	                SecurityContextHolder.getContext().setAuthentication(authToken); // Set authentication
	            }
	        }
	        chain.doFilter(request, response); // Continue the filter chain
	    }


}
