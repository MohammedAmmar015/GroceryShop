package com.ideas2it.groceryshop.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * It is used to set data and store in model object.
 * It is used to create user profile.
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@Getter
@Setter
public class UserRequestDto {

    private String userName;
    private String firstName;
    private String lastName;
    private Long mobileNumber;
    private String email;
    private String password;
    private RoleRequestDto roleRequestDto;
}
