package com.ideas2it.groceryshop.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.model.User;

/**
 *
 * It is used to convert dto into model and vice versa
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
public class UserMapper {

    /**
     *  It is used to convert UserRequestDto to User object
     *
     * @param userRequestDto
     * @return user
     */
    public static User userRequestDtoToUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setMobileNumber(userRequestDto.getMobileNumber());
        user.setEmail(userRequestDto.getEmail());
        user.setRole(RoleMapper.roleDtoToRole(userRequestDto.getRoleDto()));
        return user;
    }

    /**
     * It is used to convert User to UserResponseDto Object
     *
     * @param user
     * @return userResponseDto
     */
    public static UserResponseDto userToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setMobileNumber(user.getMobileNumber());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setCreatedAt(user.getCreatedAt());
        userResponseDto.setModifiedAt(user.getModifiedAt());
        userResponseDto.setCreatedBy(userResponseDto.getCreatedBy());
        userResponseDto.setRole(user.getRole());
        userResponseDto.setIsActive(user.getIsActive());
        //userResponseDto.setAddresses(user.getAddresses());
        return userResponseDto;
    }

    /**
     * It is used to convert list of User object to UserResponseDto object list
     *
     * @param userList
     * @return userResponseDtoList
     */
    public static List<UserResponseDto> userToUserResponseDtoList(List<User> userList) {
        List<UserResponseDto> userResponseDtoList = new ArrayList<UserResponseDto>();
        for(User user : userList) {
            userResponseDtoList.add(userToUserResponseDto(user));
        }
        return userResponseDtoList;
    }
}
