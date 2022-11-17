/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ideas2it.groceryshop.filter.CustomSecurityFilter;

/**
 *
 * It is used to Configure spring security
 * It contains access restriction method which will limit user from
 * accessing all API restrict
 *
 * @version 1.0
 * @author Rohit A P
 * @since 07-11-2022
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final CustomSecurityFilter customSecurityFilter;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          CustomSecurityFilter customSecurityFilter) {
        this.userDetailsService = userDetailsService;
        this.customSecurityFilter = customSecurityFilter;
    }

    /**
     * This method is used to create bean for BCryptPasswordEncoder
     *
     * @return new BCryptPasswordEncoder it returns new
     * BCryptPasswordEncoder object
     */
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method is used to create bean for DaoAuthenticationProvider
     *
     * @return authenticationProvider it is used authenticate user based
     *         on username and password
     * @throws AuthenticationException it contains invalid credentials message
     */
    @Bean
    public DaoAuthenticationProvider authenticationManagerBean()
            throws AuthenticationException {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return authenticationProvider;
    }

    /**
     * This method is used to restrict user from accessing all api
     *
     * @param http it is used to configure security filter
     * @return http.build() it contains authorization based on role
     * @throws Exception it contains exception message
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/v1/login",
                        "/api/v1/users").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/categories/",
                        "/api/v1/products/", "/api/v1/stores", "/api/v1/stocks/*/*",
                        "/api/v1/roles")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/users", "/api/v1/users/",
                        "/api/v1/orders/cancelledOrders",
                        "/api/v1/orders/date/*", "/api/v1/stocks/*",
                        "/api/v1/stocks/*/*", "/api/v1/stores", "/api/v1/orders/products/*",
                        "/api/v1/users/*/role")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/categories/*",
                        "/api/v1/products/*", "/api/v1/roles/*","/api/v1/stocks/*",
                        "/api/v1/stocks/*/*", "/api/v1/stores/*",
                        "/api/v1/categories" +
                                "/subCategories/*/*")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/users/*",
                        "/api/v1/categories/*", "/api/v1/products/*",
                        "/api/v1/stocks/*", "/api/v1/stores/*",
                        "/api/v1/categories/subCategories/*/*",
                        "/api/v1/roles/*")
                .hasRole("ADMIN")
                .antMatchers("/api/v1/orders/ordersDelivery/*").
                hasRole("DELIVERY_PERSON")
                .antMatchers("/api/v1/orders/activeOrders")
                .hasAnyRole("ADMIN", "DELIVERY_PERSON")
                .anyRequest().authenticated()
                .and().httpBasic();
        http.addFilterBefore(customSecurityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}