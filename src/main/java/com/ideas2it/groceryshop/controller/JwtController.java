package com.ideas2it.groceryshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.groceryshop.dto.LoginRequestDto;
import com.ideas2it.groceryshop.dto.LoginResponseDto;
import com.ideas2it.groceryshop.util.JwtUtil;

/**
 * Jwt stands for JSON web token it is used to generate
 * token based on user details authentication
 *
 * @version 1.0 09-11-2022
 *
 * @author Rohit A P
 */
@RestController
@RequestMapping("api/v1/login")
public class JwtController {

    private DaoAuthenticationProvider authenticationProvider;
    private JwtUtil jwtTokenUtil;
    private UserDetailsService userDetailsService;

    @Autowired
    public JwtController(DaoAuthenticationProvider authenticationProvider, JwtUtil jwtTokenUtil
            , UserDetailsService userDetailsService) {
        this.authenticationProvider = authenticationProvider;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public LoginResponseDto createAuthenticationToken(@RequestBody LoginRequestDto loginRequestDto)
            throws Exception {
        authenticate(loginRequestDto.getUserName(), loginRequestDto.getPassword());
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginRequestDto.getUserName());
        String token = jwtTokenUtil.generateToken(userDetails);
        return new LoginResponseDto(token);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username
                    , password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }
}
