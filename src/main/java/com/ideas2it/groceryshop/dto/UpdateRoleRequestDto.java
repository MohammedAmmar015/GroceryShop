package com.ideas2it.groceryshop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * It is used to update role name
 *
 * @version 1.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UpdateRoleRequestDto {

    @NotNull
    @NotBlank(message = "Name to update Field cannot be empty")
    private String nameToUpdate;

    @NotNull
    @NotBlank(message = "Name Field cannot be empty")
    private String name;
}