package com.gradingsystem.gradingsystem.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    private final String secretKey = "79e727ef0c3a866e810b591c7932d5da0d7d5bf81c87ae01c12ca4660c210a978290c37540f1e5d80b56d6f468ba82e159d4d5501ced49341e530d943cba4e3e";

    private final long expirationTime = 1000 * 60 * 60; // 1 hour

    private final byte[] decodedSecretKey = Base64.getDecoder().decode(secretKey);

    @SuppressWarnings("deprecation")
    public String generateToken(String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles); 

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, decodedSecretKey) 
                .compact();
    }

    // Validate Token with roles
    public boolean validateToken(String token, UserDetails userDetails) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return (List<String>) claims.get("roles");
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        try {
            System.out.println("Decoded secret key: " + Base64.getEncoder().encodeToString(decodedSecretKey));

            return Jwts.parserBuilder()
                    .setSigningKey(decodedSecretKey) 
                    .build()
                    .parseClaimsJws(token) 
                    .getBody();
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            
            System.out.println("JWT token has expired: " + e.getMessage());
            throw e;
        } catch (io.jsonwebtoken.MalformedJwtException e) {
      
            System.out.println("JWT token is malformed: " + e.getMessage());
            throw e;
        } catch (io.jsonwebtoken.SignatureException e) {
 
            System.out.println("JWT token signature is invalid: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("Error parsing JWT token: " + e.getMessage());
            throw e;
        }
    }
}
