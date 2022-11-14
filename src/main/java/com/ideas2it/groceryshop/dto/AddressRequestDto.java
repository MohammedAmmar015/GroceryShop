package com.ideas2it.groceryshop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * It is used to set data and transfer to model object for creating.
 * It is used to create address for user
 *
 * @version 1.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class AddressRequestDto {

    @NotNull
    @NotBlank(message = "Street Field cannot be empty")
    private String street;

    @NotNull
    @NotBlank(message = "Area Field cannot be empty")
    private String area;

    @NotNull
    @NotBlank(message = "PinCode Field cannot be empty")
    private Integer pinCode;

    @NotNull
    @NotBlank(message = "landMark Field cannot be empty")
    private String landMark;
}