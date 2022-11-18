/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.RoleUpdateRequestDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.RoleMapper;
import com.ideas2it.groceryshop.model.Role;
import com.ideas2it.groceryshop.repository.RoleRepository;
import com.ideas2it.groceryshop.service.RoleService;

/**
 *
 * It is implements class of RoleService
 * It is used to save, update and delete role from database
 * Dto objects are converted into model object using mapper
 * for storing in database and vice versa.
 *
 * @version 1.0
 * @author Rohit A P
 * @since 07-11-2022
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final Logger logger = LogManager.getLogger(RoleServiceImpl.class);

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * It is used to create role
     *
     * @param roleRequestDto it contains role name
     * @return SuccessResponseDto it returns success message
     * @throws ExistedException role already exist
     */
    @Override
    public SuccessResponseDto addRole(RoleRequestDto roleRequestDto) throws ExistedException {
        logger.debug("Entered addRole method");
        Role role = RoleMapper.roleDtoToRole(roleRequestDto.getName());
        Boolean isAvailable = roleRepository.existsByNameAndIsActive( role.getName(),
                true);
        if(isAvailable == true) {
            logger.debug("Role already exist");
            throw new ExistedException("Role already exist");
        }
        roleRepository.save(role);
        logger.debug("Role created successfully");
        return new SuccessResponseDto(201,"Role created successfully");
    }

    /**
     * it is used to update role
     *
     * @param roleUpdateRequestDto it contains role name and name to be updated
     * @return SuccessResponseDto it returns success message
     * @throws NotFoundException role not found
     */
    @Override
    public SuccessResponseDto updateRole(RoleUpdateRequestDto roleUpdateRequestDto)
            throws NotFoundException {
        logger.debug("Entered updateRole method");
        Optional<Role> role = roleRepository.findByIsActiveAndName(true,
                RoleMapper.roleDtoToRole(roleUpdateRequestDto.getNameToUpdate()).getName());
        if(role.isEmpty()) {
            logger.debug("Role not found");
            throw new NotFoundException("Role not found");
        }
        roleRepository.updateRoleName(RoleMapper.roleDtoToRole
                        (roleUpdateRequestDto.getName()).getName(),
                RoleMapper.roleDtoToRole(roleUpdateRequestDto.
                        getNameToUpdate()).getName());
        logger.debug("Role updated successfully");
        return new SuccessResponseDto(200,"Role updated successfully");
    }

    /**
     * It is uses to delete role by role name
     *
     * @param roleRequestDto it is used to delete role
     * @return SuccessResponseDto it returns success message
     * @throws NotFoundException role not found
     */
    @Override
    public SuccessResponseDto deleteRole(RoleRequestDto roleRequestDto) throws NotFoundException {
        logger.debug("Entered deleteRole method");
        Optional<Role> role = roleRepository.findByIsActiveAndName
                (true, roleRequestDto.getName());
        if(role.isEmpty()) {
            logger.debug("Role not found");
            throw new NotFoundException("Role not found");
        }
        roleRepository.deactivateRole(roleRequestDto.getName());
        logger.debug("Role deleted successfully");
        return new SuccessResponseDto(200,"Role deleted successfully");
    }

    /**
     * This method is used to find role by name
     *
     * @param name it is name of role
     * @return role it returns role object
     */
    @Override
    public Optional<Role> findRoleByName(String name) {
        logger.debug("Entered findRoleByName");
        Optional<Role> role = roleRepository.findByIsActiveAndName(true, name);
        logger.debug("Got optional role object");
        return role;
    }
}