/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.helper;

import java.util.Optional;

import com.ideas2it.groceryshop.configuration.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.repository.UserRepo;
import com.ideas2it.groceryshop.model.User;

/**
 *  This Class is used to help other service classes
 *
 * @version 1.0
 * @author Rohit A P
 * @since 07-11-2022
 */
@Service
public class UserHelper {

    @Autowired
    private UserRepo userRepo;

    /**
     *  It is used to get user by id
     *
     * @param id is user id
     * @return user
     */
    public Optional<User> findUserById(Integer id) {
        Optional<User> user = userRepo.findByIsActiveAndId(true, id);
        return user;
    }

    /**
     * This method is used to get current user object
     *
     * @return user it contains user details
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.
                getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return user.getUser();
    }
}