package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.helper.UserHelper;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.UserRequestDto;

import com.ideas2it.groceryshop.mapper.UserMapper;

import com.ideas2it.groceryshop.model.User;

import com.ideas2it.groceryshop.repository.RoleRepo;
import com.ideas2it.groceryshop.repository.UserRepo;

import com.ideas2it.groceryshop.service.UserService;

import java.util.List;
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

    UserHelper userHelper = new UserHelper();

    /**
     * it is used to create user
     *
     * @param userRequestDto
     */
    public void addUser(UserRequestDto userRequestDto) {
        User user = UserMapper.userRequestDtoToUser(userRequestDto);
        if (userRequestDto.getRoleDto().getName().equals("customer")) {
            Cart cart = new Cart();
            user.setCart(cart);
        }
        Optional<Role> role =
                roleRepo.findByIsActiveAndName(true, userRequestDto.getRoleDto().getName());
        if (role.isPresent()) {
            user.setRole(role.get());
        }
        userRepo.save(user);
    }

    /**
     * It is used to get user by id
     *
     * @param id
     * @return
     */
    public UserResponseDto getUserById(Integer id) {
        User user = userRepo.findByIsActiveAndId(true, id);
        UserResponseDto userResponseDto = UserMapper.userToUserResponseDto(user);
        return userResponseDto;
    }

    public List<UserResponseDto> getAllUser() {
        List<UserResponseDto> userResponseDtoList
                = UserMapper.userToUserResponseDtoList(userRepo.findByIsActive(true));
        return userResponseDtoList;
    }

    public List<UserResponseDto> getUserByRole(String name) {
        Optional<Role> role = roleRepo.findByIsActiveAndName(true, name);
        List<UserResponseDto> userResponseDtoList
                = UserMapper.userToUserResponseDtoList
                (userRepo.findByIsActiveAndRole(true, role.get()));
        return userResponseDtoList;
    }

    public void deleteUserById(Integer id){
        userRepo.deactivateUser(id);
    }
}
