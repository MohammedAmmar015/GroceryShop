/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import java.util.Optional;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.RoleUpdateRequestDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.model.Role;

/**
 *
 * It is interface of RoleService it contains every method
 *  * present in RoleServiceImpl
 *
 * @version 1.0
 * @author Rohit A P
 * @since 07-11-2022
 */
public interface RoleService {

    /**
     * It is used to create role
     *
     * @param roleRequestDto it contains role name
     * @return SuccessResponseDto it returns success message
     * @throws ExistedException role already exist
     */
    SuccessResponseDto addRole(RoleRequestDto roleRequestDto) throws ExistedException;

    /**
     * it is used to update role
     *
     * @param roleUpdateRequestDto it contains role name and name to be updated
     * @return SuccessResponseDto it returns success message
     * @throws NotFoundException role not found
     */
    SuccessResponseDto updateRole(RoleUpdateRequestDto roleUpdateRequestDto)
                                                                    throws NotFoundException;

    /**
     * It is uses to delete role
     *
     * @param roleRequestDto it is used to delete role
     * @return SuccessResponseDto it returns success message
     * @throws NotFoundException role not found
     */
    SuccessResponseDto deleteRole(RoleRequestDto roleRequestDto) throws NotFoundException;

    /**
     * This method is used to find role by name
     *
     * @param name it is name of role
     * @return role it returns role object
     */
    public Optional<Role> findRoleByName(String name);
}
