package com.ideas2it.groceryshop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * It is used to create role
 *
 * @version 1.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@Getter
@Setter
public class RoleRequestDto {

    @NotNull
    @NotBlank(message = "Name Field cannot be empty")
    private String name;
}