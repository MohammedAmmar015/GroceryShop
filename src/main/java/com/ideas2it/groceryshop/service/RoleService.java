/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.RoleUpdateRequestDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;

/**
 *
 * It is interface of RoleService
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
     * @throws Existed role already exist
     */
    SuccessResponseDto addRole(RoleRequestDto roleRequestDto) throws Existed;

    /**
     * it is used to update role
     *
     * @param roleUpdateRequestDto it contains role name and name to be updated
     * @return SuccessResponseDto it returns success message
     * @throws NotFound role not found
     */
    SuccessResponseDto updateRole(RoleUpdateRequestDto roleUpdateRequestDto) throws NotFound;

    /**
     * It is uses to delete role
     *
     * @param roleRequestDto it is used to delete role
     * @return SuccessResponseDto it returns success message
     * @throws NotFound role not found
     */
    SuccessResponseDto deleteRole(RoleRequestDto roleRequestDto) throws NotFound;
}
