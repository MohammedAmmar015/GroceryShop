package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.RoleDto;
import com.ideas2it.groceryshop.model.Role;

/**
 *
 * It is used to convert dto into model and vice versa
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
public class RoleMapper {

    public static Role roleDtoToRole(RoleDto roleDto){
        Role role = new Role();
        role.setName(roleDto.getName());
        return role;
    }
}
