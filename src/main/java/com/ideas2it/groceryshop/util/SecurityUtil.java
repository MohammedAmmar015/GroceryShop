package com.ideas2it.groceryshop.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * SecurityUtil class is responsible for creating token
 * and validate token
 *
 * @version 1.0
 *
 * @author Rohit A P
 * @since 07-11-2022
 */
@Component
public class SecurityUtil {
    @Value("${jwt.secret}")
    private String secret;

    /**
     * This method is used to get username from token
     *
     * @param token it is generated token
     * @return it returns username
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * This method is used to get claim from token
     *
     * @param token it is token of user
     * @param claimsResolver it is used to apply claims
     * @return it returns claimsResolver
     * @param <T> it contains token, function<claims and claimsResolver
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * This method is used to get all claims from token
     *
     * @param token it is token of user
     * @return it returns claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * This method is used to generate based on username and claims
     *
     * @param userDetails it contains username and password
     *                   doGenerateToken method
     * @return it returns bearer token from
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * This method is used to generate bearer token
     *
     * @param claims is used to set secret key
     * @param subject it is username
     * @return it returns bearer token
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * This method is used to validate token
     *
     * @param token it is the token to be validated
     * @param userDetails it contains username and password
     * @return If token is valid it returns true else false.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()));
    }
}