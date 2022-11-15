package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.model.Role;

/**
 *
 * It is used to convert dto into model
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
public class RoleMapper {

    /**
     * It is used to convert role Dto object into model object
     *
     * @param roleRequest it contains role name
     * @return role it returns role object
     */
    public static Role roleDtoToRole(String roleRequest){
        Role role = new Role();
        String roleValue = "ROLE_" + roleRequest;
        role.setName(roleValue.toUpperCase());
        return role;
    }
}