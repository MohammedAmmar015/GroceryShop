package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.UserRequestDto;
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

    public static User userRequestDtoToUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setMobileNumber(userRequestDto.getMobileNumber());
        user.setEmail(userRequestDto.getEmail());
        user.setRole(RoleMapper.roleDtoToRole(userRequestDto.getRoleDto()));
        return user;
    }
}
