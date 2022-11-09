package com.ideas2it.groceryshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.dto.UpdateRoleRequestDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.RoleService;

/**
 *
 * Role class is used to do create, update and delete role.
 *
 * @version 19.0 07-11-2022
 *
 * @author Rohit A P
 *
 */
@RestController
@RequestMapping("api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * It is used to create role
     *
     * @param roleRequestDto it is contains name of role
     * @return SuccessDto it returns success message
     */
    @PostMapping
    public SuccessDto createRole(@RequestBody RoleRequestDto roleRequestDto) throws Existed {
        return roleService.addRole(roleRequestDto);
    }

    /**
     * It is used to update name of existing role
     *
     * @param updateRoleRequestDto it contains old name of role to be updated and name of role
     * @return SuccessDto it returns success message
     */
    @PutMapping
    public SuccessDto updateRole(@RequestBody UpdateRoleRequestDto updateRoleRequestDto)
            throws NotFoundException {
        return roleService.updateRole(updateRoleRequestDto);
    }

    /**
     * It is used to delete role by name
     *
     * @param roleRequestDto it contains role name
     * @return SuccessDto it returns success message
     */
    @DeleteMapping
    public SuccessDto deleteRole(@RequestBody RoleRequestDto roleRequestDto)
            throws NotFoundException {
        return roleService.deleteRole(roleRequestDto);
    }
}
