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
        user.setRole(RoleMapper.roleDtoToRole(userRequestDto.getRoleRequestDto()));
        System.out.print(user);
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
        userResponseDto.setModifiedBy(user.getModifiedBY());
        userResponseDto.setRole(user.getRole());
        userResponseDto.setIsActive(user.getIsActive());
        return userResponseDto;
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
