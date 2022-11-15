package com.ideas2it.groceryshop.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.RoleUpdateRequestDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.mapper.RoleMapper;
import com.ideas2it.groceryshop.model.Role;
import com.ideas2it.groceryshop.repository.RoleRepo;
import com.ideas2it.groceryshop.service.RoleService;

/**
 *
 * It is implements class of RoleService
 * It is used to save, update and delete role from database
 *
 * @version 1.0
 * @author Rohit A P
 * @since 07-11-2022
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepo roleRepo;

    /**
     * It is used to create role
     *
     * @param roleRequestDto it contains role name
     * @return SuccessResponseDto it returns success message
     * @throws Existed role already exist
     */
    @Override
    public SuccessResponseDto addRole(RoleRequestDto roleRequestDto) throws Existed {
        Role role = RoleMapper.roleDtoToRole(roleRequestDto.getName());
        Boolean isAvailable = roleRepo.existsByNameAndIsActive( role.getName(),
                true);
        if(isAvailable == true) {
            throw new Existed("Role already exist");
        }
        roleRepo.save(role);
        return new SuccessResponseDto(200,"Role created successfully");
    }

    /**
     * it is used to update role
     *
     * @param roleUpdateRequestDto it contains role name and name to be updated
     * @return SuccessResponseDto it returns success message
     * @throws NotFound role not found
     */
    @Override
    public SuccessResponseDto updateRole(RoleUpdateRequestDto roleUpdateRequestDto) throws NotFound {
        Optional<Role> role = roleRepo.findByIsActiveAndName(true,
                RoleMapper.roleDtoToRole(roleUpdateRequestDto.getNameToUpdate()).getName());
        if(role.isEmpty()) {
            throw new NotFound("Role not found");
        }
        roleRepo.updateRoleName(RoleMapper.roleDtoToRole(roleUpdateRequestDto.getName()).getName(),
                RoleMapper.roleDtoToRole(roleUpdateRequestDto.getNameToUpdate()).getName());
        return new SuccessResponseDto(200,"Role updated successfully");
    }

    /**
     * It is uses to delete role
     *
     * @param roleRequestDto it is used to delete role
     * @return SuccessResponseDto it returns success message
     * @throws NotFound role not found
     */
    @Override
    public SuccessResponseDto deleteRole(RoleRequestDto roleRequestDto) throws NotFound {
        Optional<Role> role = roleRepo.findByIsActiveAndName
                (true, roleRequestDto.getName());
        if(role.isEmpty()) {
            throw new NotFound("Role not found");
        }
        roleRepo.deactivateRole(roleRequestDto.getName());
        return new SuccessResponseDto(200,"Role deleted successfully");
    }
}