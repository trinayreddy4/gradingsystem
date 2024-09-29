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

    // Use a fixed secret key for consistency (encoded in Base64 for security)
    private final String secretKey = "79e727ef0c3a866e810b591c7932d5da0d7d5bf81c87ae01c12ca4660c210a978290c37540f1e5d80b56d6f468ba82e159d4d5501ced49341e530d943cba4e3e";

    // Define the token expiration time (e.g., 1 hour)
    private final long expirationTime = 1000 * 60 * 60; // 1 hour

    // Decode the secret key once for reuse
    private final byte[] decodedSecretKey = Base64.getDecoder().decode(secretKey);

    // Generate JWT Token with roles
    @SuppressWarnings("deprecation")
    public String generateToken(String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles); // Store roles in claims

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, decodedSecretKey) // Specify HS256 for signing
                .compact();
    }

    // Validate Token with roles
    public boolean validateToken(String token, UserDetails userDetails) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Extract Username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract Roles
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return (List<String>) claims.get("roles");
    }

    // Check if Token is Expired
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Extract All Claims
    private Claims extractAllClaims(String token) {
        try {
            // Debugging: Print the decoded secret key used
            System.out.println("Decoded secret key: " + Base64.getEncoder().encodeToString(decodedSecretKey));

            return Jwts.parserBuilder()
                    .setSigningKey(decodedSecretKey) // Use the decoded secret key
                    .build()
                    .parseClaimsJws(token) // Parse the JWT token
                    .getBody(); // Extract claims
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            // Handle expired token
            System.out.println("JWT token has expired: " + e.getMessage());
            throw e;
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            // Handle malformed token
            System.out.println("JWT token is malformed: " + e.getMessage());
            throw e;
        } catch (io.jsonwebtoken.SignatureException e) {
            // Handle invalid signature
            System.out.println("JWT token signature is invalid: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            // Handle other parsing issues
            System.out.println("Error parsing JWT token: " + e.getMessage());
            throw e;
        }
    }
}
