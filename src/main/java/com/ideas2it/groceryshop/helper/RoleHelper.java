/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.helper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.model.Role;
import com.ideas2it.groceryshop.repository.RoleRepo;

/**
 * This class is used to help service classes
 *
 * @version 1.0
 * @author Rohit A P
 * @since 17-11-2022
 */
@Service
public class RoleHelper {

    @Autowired
    RoleRepo roleRepo;

    /**
     * This method is used to find role by name
     *
     * @param name it is name of role
     * @return role it returns role object
     */
    public Optional<Role> findRoleByName(String name) {
        Optional<Role> role = roleRepo.findByIsActiveAndName(true, name);
        return role;
    }
}