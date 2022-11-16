/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ideas2it.groceryshop.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Custom Jwt filter class is used to check if bearer token is valid
 * or not and give authentication
 *
 * @version 1.0 07-11-2022
 * @author Rohit A P
 */
@Component
public class CustomSecurityFilter extends OncePerRequestFilter {
    private UserDetailsService userDetailsService;
    private SecurityUtil securityUtil;

    @Autowired
    public CustomSecurityFilter(UserDetailsService userDetailsService,
                                SecurityUtil securityUtil) {
        this.userDetailsService = userDetailsService;
        this.securityUtil = securityUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IllegalArgumentException, ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    username = securityUtil.getUsernameFromToken(jwtToken);
                } catch (IllegalArgumentException illegalArgumentException) {
                    throw new IllegalArgumentException("Unable to get JWT Token");
                }
            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails =
                        this.userDetailsService.loadUserByUsername(username);

                if (securityUtil.validateToken(jwtToken, userDetails)) {

                    UsernamePasswordAuthenticationToken
                            usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken
                                    (userDetails, null,
                                            userDetails.getAuthorities());
                    SecurityContextHolder.getContext().
                            setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}