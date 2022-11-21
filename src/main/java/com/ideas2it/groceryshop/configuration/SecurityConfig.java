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
 * <p>
 *    Providing service to restricts users from accessing all API and restrict them
 *    based on their role.
 * </p>
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
     * <p>
     *     Creating bean for BCryptPasswordEncoder
     * </p>
     *
     * @return BCryptPasswordEncoder - password encoder
     */
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * <p>
     *     Creating bean for DaoAuthenticationProvider
     * </p>
     *
     * @return authenticationProvider - authenticate user based
     *         on username and password
     * @throws AuthenticationException - contains invalid credentials message
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
     * <p>
     *     Restricts users from accessing all api
     * </p>
     *
     * @param http - to configure security filter
     * @return http.build() - contains authorization based on role
     * @throws Exception - contains exception message
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/v1/login",
                        "/api/v1/users").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/categories",
                        "/api/v1/products", "/api/v1/stores",
                        "/api/v1/stocks/location/*/products/*",
                        "/api/v1/users/roles")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/users", "/api/v1/users/",
                        "/api/v1/user/orders/cancelledOrders",
                        "/api/v1/user/orders/*/*",
                        "/api/v1/user/orders/date/*/user/*",
                        "/api/v1/user/orders/date/*", "/api/v1/stocks/products/*",
                        "/api/v1/stocks/location/*/products/*", "/api/v1/stores",
                        "/api/v1/user/orders/products/*",
                        "/api/v1/users/*/role")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/categories/*",
                        "/api/v1/products/*", "/api/v1/users/roles/*",
                        "/api/v1/stocks/location/*/products/*",
                        "/api/v1/stocks/*/*", "/api/v1/stores/*",
                        "/api/v1/categories" +
                                "/subCategories/*/*")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/users/*",
                        "/api/v1/categories/*", "/api/v1/products/*",
                        "/api/v1/stocks/*", "/api/v1/stores/*",
                        "/api/v1/categories/subCategories/*/*",
                        "/api/v1/users/roles/*")
                .hasRole("ADMIN")
                .antMatchers("/api/v1/orders/*/orderDelivery")
                .hasRole("DELIVERY_PERSON")
                .antMatchers("/api/v1/orders/activeOrders")
                .hasAnyRole("ADMIN", "DELIVERY_PERSON")
                .antMatchers(HttpMethod.PUT, "/api/v1/user/carts/*",
                        "/api/v1/user/orders/*/cancelOrder")
                .hasRole("CUSTOMER")
                .antMatchers(HttpMethod.POST , "/api/v1/user/orders/*",
                        "/api/v1/user/carts")
                .hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/api/v1/user/orders/*",
                        "/api/v1/user/orders")
                .hasRole("CUSTOMER")
                .anyRequest().authenticated()
                .and().httpBasic();
        http.addFilterBefore(customSecurityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}