package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;

import java.util.List;

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

    public void addUser(UserRequestDto userRequestDto);

    public UserResponseDto getUserById(Integer id);

    public List<UserResponseDto> getAllUser();

    public List<UserResponseDto> getUserByRole(String name);

    public void deleteUserById(Integer id);
}
