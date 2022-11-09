package com.ideas2it.groceryshop.service;

import java.util.List;

import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFoundException;

/**
 *
 * It is interface of UserService implements
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
public interface UserService {

    /**
     * it is used to create user
     *
     * @param userRequestDto it contains user details
     * @return SuccessDto returns success message
     * @throws Existed if username already exist
     */
    SuccessDto addUser(UserRequestDto userRequestDto) throws Existed;

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
     */
    List<UserResponseDto> getUserByRole(String name);

    /**
     *  It is used to delete user by id
     *
     * @param id to be deleted
     * @return successDto returns success message
     * @throws NotFoundException user does not exist
     */
    SuccessDto deleteUserById(Integer id) throws NotFoundException;
}