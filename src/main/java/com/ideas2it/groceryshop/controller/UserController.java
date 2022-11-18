/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.dto.UserUpdateDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.UserService;

/**
 *
 * UserController is providing services for
 * create, delete, update and view users
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     *  It is used to create user
     *
     * @param userRequestDto it contains user detail
     * @throws ExistedException username already exist
     */
    @PostMapping
    public SuccessResponseDto createUser(@Valid @RequestBody UserRequestDto userRequestDto)
            throws ExistedException {
        logger.debug("Entered createUser method");
        return userService.addUser(userRequestDto);
    }

    /**
     * It is used to get user by id
     *
     * @param id it is id of user
     * @return userResponseDto
     * @throws NotFoundException user does not exist
     */
    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable("userId") Integer id)
                                       throws NotFoundException {
        logger.debug("Entered getUserById method");
        UserResponseDto userResponseDto = userService.getUserById(id);
        return userResponseDto;
    }

    /**
     * It is used get all users
     *
     * @return userResponseDtoList
     * @throws NotFoundException users does not found
     */
    @GetMapping
    public List<UserResponseDto> viewAllUser() throws NotFoundException {
        logger.debug("Entered viewAllUser method");
        List<UserResponseDto> userResponseDtoList = userService.getAllUser();
        logger.debug("Got list of user");
        return userResponseDtoList;
    }

    /**
     * It is used to find user by role
     *
     * @param name it is name of role
     * @return userResponseDtoList it returns list of user
     * @throws NotFoundException users not found
     */
    @GetMapping("/role/{roleName}")
    public List<UserResponseDto> viewUsersByRole
    (@PathVariable("roleName") String name) throws NotFoundException {
        logger.debug("Entered viewUsersByRole method");
        List<UserResponseDto> userResponseDtoList = userService.getUserByRole(name);
        logger.debug("Got list of user");
        return userResponseDtoList;
    }

    /**
     * This method is used to get current logged-in user profile
     *
     * @return UserResponseDto it contains user current user profile
     */
    @GetMapping("/userProfile")
    public UserResponseDto viewProfile() {
        logger.debug("Entered viewProfile method");
        return userService.getCurrentUserProfile();
    }

    /**
     *  It is used to delete user by id
     *
     * @param id it is id of user to be deleted
     * @return SuccessResponseDto it contains success message
     * @throws NotFoundException it contains user not found exception
     */
    @DeleteMapping("/{userId}")
    public SuccessResponseDto deleteUserById(@PathVariable("userId") Integer id)
            throws NotFoundException {
        logger.debug("Entered deleteUserById method");
        return userService.deleteUserById(id);
    }

    /**
     * This method is used to update user by username.
     * Following are the fields that can be updated by this method,
     * firstName, lastName, password and email
     *
     * @return SuccessResponseDto it contains success message
     * @throws NotFoundException user does not exist
     */
    @PutMapping
    public SuccessResponseDto updateUserByUserName
                      (@Valid @RequestBody UserUpdateDto userUpdateDto) throws NotFoundException {
        logger.debug("Entered updateUserByUserName");
        return userService.updateUserByUserName(userUpdateDto);
    }
}