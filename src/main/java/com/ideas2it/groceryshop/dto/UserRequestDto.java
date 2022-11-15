package com.ideas2it.groceryshop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * It is used to set data and store in model object.
 * It is used to create user profile.
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UserRequestDto {

    @NotNull
    @NotBlank(message = "Username Field cannot be empty")
    private String userName;

    @NotNull
    @NotBlank(message = "First name Field cannot be empty")
    private String firstName;

    @NotNull
    @NotBlank(message = "Last name Field cannot be empty")
    private String lastName;

    @NotNull(message = "Mobile Number Field cannot be empty")
    private Long mobileNumber;

    @NotNull
    @NotBlank(message = "Email Field cannot be empty")
    private String email;

    @NotNull
    @NotBlank(message = "Password Field cannot be empty")
    private String password;

    @NotNull
    @NotBlank(message = "Role Field cannot be empty")
    private String role;
}