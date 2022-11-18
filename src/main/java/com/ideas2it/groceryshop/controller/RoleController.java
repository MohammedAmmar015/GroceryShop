/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.RoleUpdateRequestDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.RoleService;

/**
 *
 * Role controller class is providing service to create,
 * update and delete role.
 *
 * @version 1.0
 * @author Rohit A P
 * @since 11-11-2022
 */
@RestController
@RequestMapping("api/v1/users/roles")
public class RoleController {

    private final RoleService roleService;
    private final Logger logger = LogManager.getLogger(RoleController.class);

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * It is used to create role
     *
     * @param roleRequestDto it is contains name of role
     * @return SuccessResponseDto it returns success message
     */
    @PostMapping
    public SuccessResponseDto createRole(@Valid @RequestBody RoleRequestDto roleRequestDto)
                                         throws ExistedException {
        logger.debug("Entered createRole method");
        return roleService.addRole(roleRequestDto);
    }

    /**
     * It is used to update name of existing role
     *
     * @param roleUpdateRequestDto it contains old name of role to
     *                            be updated and name of role
     * @return SuccessResponseDto it returns success message
     */
    @PutMapping
    public SuccessResponseDto updateRole
    (@Valid @RequestBody RoleUpdateRequestDto roleUpdateRequestDto) throws NotFoundException {
        logger.debug("Entered updateRole method");
        return roleService.updateRole(roleUpdateRequestDto);
    }

    /**
     * It is used to delete role by name
     *
     * @param roleRequestDto it contains role name
     * @return SuccessResponseDto it returns success message
     */
    @DeleteMapping
    public SuccessResponseDto deleteRole(@Valid @RequestBody RoleRequestDto roleRequestDto)
            throws NotFoundException {
        logger.debug("Entered deleteRole method");
        return roleService.deleteRole(roleRequestDto);
    }
}