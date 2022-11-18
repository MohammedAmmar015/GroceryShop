/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import java.util.List;

import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.dto.UserUpdateDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.model.User;

/**
 * UserService is used to save, update, delete and
 * retrieve user data from database.
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
public interface UserService {

    /**
     * it is used to create user
     *
     * @param userRequestDto it contains user details
     * @return SuccessResponseDto returns success message
     * @throws ExistedException if username already exist
     */
    SuccessResponseDto addUser(UserRequestDto userRequestDto) throws ExistedException;

    /**
     * It is used to get user by id
     *
     * @param id it is id of user
     * @return userResponseDto it contains user detail
     * @throws NotFoundException if user does not exist or inactive
     */
    UserResponseDto getUserById(Integer id) throws NotFoundException;

    /**
     * It is used to get all users
     *
     * @return userResponseDtoList is list of user
     * @throws NotFoundException no user found
     */
    List<UserResponseDto> getAllUser() throws NotFoundException;

    /**
     * It is used to find users by role
     *
     * @param name used to search users by role name
     * @return userResponseDtoList list of user
     * @throws NotFoundException users not found
     */
    List<UserResponseDto> getUserByRole(String name) throws NotFoundException;

    /**
     *  It is used to delete user by id
     *
     * @param id to be deleted
     * @return SuccessResponseDto returns success message
     * @throws NotFoundException user does not exist
     */
    SuccessResponseDto deleteUserById(Integer id) throws NotFoundException;

    /**
     * This method is used to get userName by mobile number.
     * Using Regex given string is validated as mobile number or username.
     * If given string is mobileNumber using mobileNumber userName is retrieved and returned.
     * Else userName is returned.
     *
     * @param userNameOrMobileNumber it contains username or mobileNumber
     * @return userName it is contains username
     */
    String getUserNameByMobileNumber(String userNameOrMobileNumber);

    /**
     * This method is used to get user object by name,
     * update user object and store in database
     *
     * @param userUpdateDto it contains details to be updated
     * @return SuccessResponseDto it contains success message
     */
    SuccessResponseDto updateUserByUserName(UserUpdateDto userUpdateDto)
                                                                 throws NotFoundException;

    /**
     * This method is used to get current user profile
     *
     * @return userResponseDto it contains user details
     */
    UserResponseDto getCurrentUserProfile();

    /**
     * This method is used to get current user object
     *
     * @return user it contains user details
     */
    User getCurrentUser();
}