package com.ideas2it.groceryshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.dto.UpdateRoleRequestDto;
import com.ideas2it.groceryshop.mapper.RoleMapper;
import com.ideas2it.groceryshop.model.Role;
import com.ideas2it.groceryshop.repository.RoleRepo;
import com.ideas2it.groceryshop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepo roleRepo;

    /**
     * It is used to create role
     *
     * @param roleRequestDto it contains role name
     */
    public void addRole(RoleRequestDto roleRequestDto){
        Role role = RoleMapper.roleDtoToRole(roleRequestDto);
        roleRepo.save(role);
    }

    /**
     * it is used to update role
     *
     * @param updateRoleRequestDto it contains role name and name to be updated
     */
    public void updateRole(UpdateRoleRequestDto updateRoleRequestDto){
        roleRepo.updateRoleName(updateRoleRequestDto.getName(), updateRoleRequestDto.getNameToUpdate());
    }

    /**
     * It is uses to delete role
     *
     * @param roleRequestDto it is used to delete role
     */
    public void deleteRole(RoleRequestDto roleRequestDto){
        roleRepo.deactivateRole(roleRequestDto.getName());
    }
}
