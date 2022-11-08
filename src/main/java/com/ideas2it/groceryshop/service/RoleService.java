package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.dto.UpdateRoleRequestDto;

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
     */
    void addRole(RoleRequestDto roleRequestDto);

    /**
     * it is used to update role
     *
     * @param updateRoleRequestDto it contains role name and name to be updated
     */
    void updateRole(UpdateRoleRequestDto updateRoleRequestDto);

    /**
     * It is uses to delete role
     *
     * @param roleRequestDto it is used to delete role
     */
    void deleteRole(RoleRequestDto roleRequestDto);
}
