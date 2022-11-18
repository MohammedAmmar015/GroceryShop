/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.groceryshop.dto.LoginRequestDto;
import com.ideas2it.groceryshop.dto.LoginResponseDto;
import com.ideas2it.groceryshop.service.UserService;
import com.ideas2it.groceryshop.util.SecurityUtil;

/**
 * AuthenticationController is providing service
 * for logging-in for user
 *
 * @version 1.0
 * @author Rohit A P
 * @since 09-11-2022
 */
@RestController
@RequestMapping("api/v1/login")
public class AuthenticationController {

    private final DaoAuthenticationProvider authenticationProvider;
    private final SecurityUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private Logger logger = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    public AuthenticationController(DaoAuthenticationProvider authenticationProvider,
                                    SecurityUtil jwtTokenUtil,
                                    UserDetailsService userDetailsService,
                                    UserService userService) {
        this.authenticationProvider = authenticationProvider;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    /**
     * This method is used to find if user exist or not and to create bearer token
     *
     * @param loginRequestDto it contains username or mobile number and password
     * @return LoginResponseDto it contains bearer token and success message
     * @throws BadCredentialsException it contains badCredentials message
     */
    @PostMapping
    public LoginResponseDto createAuthenticationToken
            (@Valid @RequestBody Optional<LoginRequestDto> loginRequestDto)
            throws BadCredentialsException {
        logger.debug("Entered createAuthenticationToken");
        SecurityContextHolder.clearContext();
        String userName = userService.getUserNameByMobileNumber
                (loginRequestDto.get().getUserNameOrMobileNumber());
        authenticate(userName, loginRequestDto.get().getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        String token = jwtTokenUtil.generateToken(userDetails);
        logger.debug("Logged in successfully");
        return new LoginResponseDto(token, "Logged in successfully", 201);
    }

    /**
     * This method is used to authenticate user by username and password
     *
     * @param username it is username of user
     * @param password it is password of user
     * @throws BadCredentialsException if credentials are false
     */
    private void authenticate(String username, String password)
            throws BadCredentialsException {
        logger.debug("Entered authenticate method");
        try {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken
                    (username, password));
        } catch (BadCredentialsException badCredentialsException) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", badCredentialsException);
        }
    }
}