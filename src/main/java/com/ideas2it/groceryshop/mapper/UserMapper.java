/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.dto.UserUpdateDto;
import com.ideas2it.groceryshop.model.User;

/**
 *
 * It is used to convert dto into model and vice versa
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
public class UserMapper {

    /**
     *  It is used to convert UserRequestDto to User object
     *
     * @param userRequestDto it contains user details
     * @return user it returns user object
     */
    public static User userRequestDtoToUser(UserRequestDto userRequestDto) {

        User user = new User();
        user.setUserName(userRequestDto.getUserName());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setMobileNumber(userRequestDto.getMobileNumber());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setRole(RoleMapper.roleDtoToRole(userRequestDto.getRole()));
        return user;
    }

    /**
     * It is used to convert User to UserResponseDto Object
     *
     * @param user it contain user details
     * @return userResponseDto it returns user details in object
     */
    public static UserResponseDto userToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUserName(user.getUserName());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setMobileNumber(user.getMobileNumber());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setCreatedAt(user.getCreatedAt());
        userResponseDto.setModifiedAt(user.getModifiedAt());
        userResponseDto.setCreatedBy(user.getCreatedBy());
        userResponseDto.setModifiedBy(user.getModifiedBy());
        userResponseDto.setRole(user.getRole().getName());
        userResponseDto.setIsActive(user.getIsActive());
        return userResponseDto;
    }

    /**
     * This method is used to convert UserUpdateDto to user object
     *
     * @param userUpdateDto it contains details to update
     * @param user it contains old details
     * @return user it contains updated details
     */
    public static User userUpdateDtoToUser(UserUpdateDto userUpdateDto,
                                           User user) {
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());
        user.setPassword(userUpdateDto.getPassword());
        user.setEmail(userUpdateDto.getEmail());
        return user;
    }

    /**
     * It is used to convert list of User object to UserResponseDto object list
     *
     * @param userList it contains list of user object
     * @return userResponseDtoList it contains list of dto object
     */
    public static List<UserResponseDto> userToUserResponseDtoList(List<User> userList) {
        List<UserResponseDto> userResponseDtoList = new ArrayList<UserResponseDto>();
        for(User user : userList) {
            userResponseDtoList.add(userToUserResponseDto(user));
        }
        return userResponseDtoList;
    }
}