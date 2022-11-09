package com.ideas2it.groceryshop.service.impl;

import java.util.Optional;

import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.dto.UpdateRoleRequestDto;
import com.ideas2it.groceryshop.model.Role;
import com.ideas2it.groceryshop.repository.RoleRepo;
import com.ideas2it.groceryshop.service.RoleService;

/**
 *
 * It is implements class of RoleService
 * It is used to save, update and delete role from database
 *
 * @version 19.0 07-11-2022
 *
 * @author Rohit A P
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepo roleRepo;

    /**
     * It is used to create role
     *
     * @param roleRequestDto it contains role name
     * @return SuccessDto it returns success message
     * @throws Existed role already exist
     */
    public SuccessDto addRole(RoleRequestDto roleRequestDto) throws Existed {
        Optional<Role> role = roleRepo.findByIsActiveAndName(true, roleRequestDto.getName());
        if(role.isPresent()) {
            throw new Existed("Role already exist");
        }
        roleRepo.save(role.get());
        return new SuccessDto(200,"Created role successfully");
    }

    /**
     * it is used to update role
     *
     * @param updateRoleRequestDto it contains role name and name to be updated
     * @return SuccessDto it returns success message
     * @throws NotFound role not found
     */
    public SuccessDto updateRole(UpdateRoleRequestDto updateRoleRequestDto) throws NotFound {
        Optional<Role> role = roleRepo.findByIsActiveAndName(true, updateRoleRequestDto.getName());
        if(role.isEmpty()) {
            throw new NotFound("Role not found");
        }
        roleRepo.updateRoleName(updateRoleRequestDto.getName(), updateRoleRequestDto.getNameToUpdate());
        return new SuccessDto(200,"Updated role successfully");
    }

    /**
     * It is uses to delete role
     *
     * @param roleRequestDto it is used to delete role
     * @return SuccessDto it returns success message
     * @throws NotFound role not found
     */
    public SuccessDto deleteRole(RoleRequestDto roleRequestDto) throws NotFound {
        Optional<Role> role = roleRepo.findByIsActiveAndName(true, roleRequestDto.getName());
        if(role.isEmpty()) {
            throw new NotFound("Role not found");
        }
        roleRepo.deactivateRole(roleRequestDto.getName());
        return new SuccessDto(200,"Deleted role successfully");
    }
}
