package com.ideas2it.groceryshop.service;

import java.util.List;

import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.dto.UserUpdateDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;

/**
 *
 * It is interface of UserService
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
     * @throws Existed if username already exist
     */
    SuccessResponseDto addUser(UserRequestDto userRequestDto) throws Existed;

    /**
     * It is used to get user by id
     *
     * @param id it is id of user
     * @return userResponseDto it contains user detail
     * @throws NotFound if user does not exist or inactive
     */
    UserResponseDto getUserById(Integer id) throws NotFound;

    /**
     * It is used to get all users
     *
     * @return userResponseDtoList is list of user
     * @throws NotFound no user found
     */
    List<UserResponseDto> getAllUser() throws NotFound;

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
     * @return SuccessResponseDto returns success message
     * @throws NotFound user does not exist
     */
    SuccessResponseDto deleteUserById(Integer id) throws NotFound;

    /**
     * This method is used to get user by mobile number
     *
     * @param mobileNumber it contains user name or
     * @return userName it is contains user name
     */
    String getUserByMobileNumber(String mobileNumber);

    /**
     * This method is used to get user object by name,
     * update user object and store in database
     *
     * @param userUpdateDto it contains details to be updated
     * @return SuccessResponseDto it contains success message
     */
    public SuccessResponseDto updateUserByUserName(UserUpdateDto userUpdateDto);
}