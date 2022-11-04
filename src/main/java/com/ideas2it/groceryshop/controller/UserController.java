package com.ideas2it.groceryshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.service.UserService;

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
    @PostMapping("/")
    public void createUser(@RequestBody UserRequestDto userRequestDto){
        userService.addUser(userRequestDto);
    }
}
