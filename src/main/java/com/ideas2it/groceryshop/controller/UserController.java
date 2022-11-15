package com.ideas2it.groceryshop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.dto.UserUpdateDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.UserService;

/**
 *
 * UserController is used to create, delete and view users
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *  It is used to create user
     * @param userRequestDto it contains user detail
     * @throws Existed username already exist
     */
    @PostMapping
    public SuccessResponseDto createUser(@Valid @RequestBody UserRequestDto userRequestDto)
            throws Existed {
        return userService.addUser(userRequestDto);
    }

    /**
     * It is used to get user by id
     *
     * @param id it is id of user
     * @return userResponseDto
     * @throws NotFound user does not exist
     */
    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable("userId") Integer id)
            throws NotFound {
        UserResponseDto userResponseDto = userService.getUserById(id);
        return userResponseDto;
    }

    /**
     * It is used get all users
     *
     * @return userResponseDtoList
     * @throws NotFound user does not found
     */
    @GetMapping
    public List<UserResponseDto> viewAllUser() throws NotFound {
        List<UserResponseDto> userResponseDtoList = userService.getAllUser();
        return userResponseDtoList;
    }

    /**
     * It is used to find user by role
     *
     * @param name it is name of role
     * @return userResponseDtoList it returns list of user
     */
    @GetMapping("/{roleName}/role")
    public List<UserResponseDto> viewUsersByRole
    (@PathVariable("roleName") String name) {
        List<UserResponseDto> userResponseDtoList =
                userService.getUserByRole(name);
        return userResponseDtoList;
    }

    /**
     *  It is used to delete user by id
     *
     * @param id it is id of user to be deleted
     * @return SuccessResponseDto it contains success message
     * @throws NotFound it contains user not found exception
     */
    @DeleteMapping("/{userId}")
    public SuccessResponseDto deleteUserById(@PathVariable("userId") Integer id)
            throws NotFound {
        return userService.deleteUserById(id);
    }

    /**
     * This method is used to update user by username.
     * Following are the fields that can be updated by this method,
     * firstName, lastName, password and phoneNumber
     *
     * @return SuccessResponseDto it contains success message
     */
    @PutMapping
    public SuccessResponseDto updateUserByUserName
                      (@Valid @RequestBody UserUpdateDto userUpdateDto){
        return userService.updateUserByUserName(userUpdateDto);
    }
}