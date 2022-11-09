package com.ideas2it.groceryshop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * It is used to Configure spring security
 *
 * @version 19.0 07-11-2022
 *
 * @author Rohit A P
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/users")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/categories", "/api/v1/products",
                        "/api/v1/stores","/api/v1/stocks")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/users", "/api/v1/users/*",
                        "api/v1/all-orders", "/api/v1/stocks**", "/api/v1/stores**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/categories/*", "/api/v1/products/*",
                        "/api/v1/roles/*","/api/v1/stocks/*", "/api/v1/stores/*")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/users", "/api/v1/categories/*",
                        "/api/v1/products/*", "/api/v1/stocks/*", "/api/v1/stores/*")
                .hasRole("ADMIN")
                .and()
                .httpBasic();
        return http.build();
    }
}