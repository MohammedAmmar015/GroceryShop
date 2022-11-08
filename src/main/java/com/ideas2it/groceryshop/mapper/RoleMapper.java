package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.model.Role;

/**
 *
 * It is used to convert dto into model
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
public class RoleMapper {

    /**
     * It is used to convert role Dto object into model object
     *
     * @param roleRequestDto it contains role name
     * @return role it returns role object
     */
    public static Role roleDtoToRole(RoleRequestDto roleRequestDto){
        Role role = new Role();
        System.out.print(roleRequestDto.getName() + "/n/n");
        role.setName(roleRequestDto.getName());
        System.out.print(role.getName());
        return role;
    }
}
