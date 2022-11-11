package com.ideas2it.groceryshop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * LoginRequestDto class is used to set and
 * get user login credentials to login
 *
 * @version 1.0 11-11-2022
 *
 * @author Rohit A P
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotNull
    @NotBlank(message = "UserName/MobileNumber Field cannot be empty")
    private String userNameOrMobileNumber;

    @NotNull
    @NotBlank(message = "password Field cannot be empty")
    private String password;
}
