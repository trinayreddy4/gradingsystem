package com.gradingsystem.gradingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(requests -> requests
	                    .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
	                    .requestMatchers("/api/assignments/**").hasAnyRole("TEACHER", "ADMIN") // Assignments accessible by Teacher and Admin
	                    .anyRequest().authenticated())
	            .httpBasic();
	    return http.build();
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}