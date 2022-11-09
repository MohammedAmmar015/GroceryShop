package com.ideas2it.groceryshop.service;

import java.util.List;

import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;

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
     * @param userRequestDto
     */
    void addUser(UserRequestDto userRequestDto);

    /**
     * It is used to get user by id
     *
     * @param id it is id of user
     * @return userResponseDto it contains user detail
     */
    UserResponseDto getUserById(Integer id);

    /**
     * It is used to get all users
     *
     * @return userResponseDtoList is list of user
     */
    List<UserResponseDto> getAllUser();

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
     */
    void deleteUserById(Integer id);
}