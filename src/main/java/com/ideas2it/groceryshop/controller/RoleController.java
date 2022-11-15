package com.ideas2it.groceryshop.controller;

import javax.validation.Valid;

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
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.RoleService;

/**
 *
 * Role controller class is used to do create,
 * update and delete role.
 *
 * @version 1.0
 * @author Rohit A P
 * @since 11-11-2022
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
     * @return SuccessResponseDto it returns success message
     */
    @PostMapping
    public SuccessResponseDto createRole(@Valid @RequestBody RoleRequestDto roleRequestDto)
            throws Existed {
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
    (@Valid @RequestBody RoleUpdateRequestDto roleUpdateRequestDto) throws NotFound {
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
            throws NotFound {
        return roleService.deleteRole(roleRequestDto);
    }
}