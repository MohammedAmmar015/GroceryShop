package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.service.UserService;

import java.util.List;

/**
 *
 * It is used to do CRUD operations
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *  it is used create user
     * @param userRequestDto
     */
    @PostMapping
    public void createUser(@RequestBody UserRequestDto userRequestDto){
        userService.addUser(userRequestDto);
    }

    /**
     * It is used to get user by id
     *
     * @param id
     * @return userResponseDto
     */
    @GetMapping("/{user-id}")
    public UserResponseDto getUserById(@PathVariable("user-id") Integer id) {
        UserResponseDto userResponseDto = userService.getUserById(id);
        return userResponseDto;
    }

    /**
     * It is used get all users
     *
     * @return userResponseDtoList
     */
    @GetMapping
    public List<UserResponseDto> viewAllUser() {
        List<UserResponseDto> userResponseDtoList = userService.getAllUser();
        return userResponseDtoList;
    }

    @GetMapping("/{role-name}/role")
    public List<UserResponseDto> viewUsersByRole(@PathVariable("role-name") String name) {
        List<UserResponseDto> userResponseDtoList = userService.getUserByRole(name);
        return userResponseDtoList;
    }

    @DeleteMapping("/{user-id}")
    public void deleteUserById(@PathVariable("user-id") Integer id){
        userService.deleteUserById(id);
    }

}
