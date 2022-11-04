package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.UserRequestDto;

import com.ideas2it.groceryshop.mapper.UserMapper;

import com.ideas2it.groceryshop.model.User;

import com.ideas2it.groceryshop.repository.RoleRepo;
import com.ideas2it.groceryshop.repository.UserRepo;

import com.ideas2it.groceryshop.service.UserService;

import java.util.Optional;

/**
 *
 * It is used to have user logics
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    /**
     * it is used to create user
     *
     * @param userRequestDto
     */
    public void addUser(UserRequestDto userRequestDto) {
        User user = UserMapper.userRequestDtoToUser(userRequestDto);
        Optional<Role> role =
                roleRepo.findRoleByIsActiveAndName(true, userRequestDto.getRoleDto().getName());
        if (role.isPresent()) {
            user.setRole(role.get());
        }
        userRepo.save(user);
    }
}
