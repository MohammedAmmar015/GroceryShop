package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.dto.UpdateRoleRequestDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;

/**
 *
 * It is interface of RoleService implements
 *
 * @version 19.0 07-11-2022
 *
 * @author Rohit A P
 *
 */
public interface RoleService {

    /**
     * It is used to create role
     *
     * @param roleRequestDto it contains role name
     * @return SuccessDto it returns success message
     * @throws Existed role already exist
     */
    SuccessDto addRole(RoleRequestDto roleRequestDto) throws Existed;

    /**
     * it is used to update role
     *
     * @param updateRoleRequestDto it contains role name and name to be updated
     * @return SuccessDto it returns success message
     * @throws NotFound role not found
     */
    SuccessDto updateRole(UpdateRoleRequestDto updateRoleRequestDto) throws NotFound;

    /**
     * It is uses to delete role
     *
     * @param roleRequestDto it is used to delete role
     * @return SuccessDto it returns success message
     * @throws NotFound role not found
     */
    SuccessDto deleteRole(RoleRequestDto roleRequestDto) throws NotFound;
}
