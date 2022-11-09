package com.ideas2it.groceryshop.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * It is used to set data and transfer to model object for creating.
 * It is used to create address for user
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@Getter
@Setter
public class AddressRequestDto {

    private String street;
    private String area;
    private Integer pinCode;
    private String landMark;
}
