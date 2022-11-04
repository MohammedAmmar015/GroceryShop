package com.ideas2it.groceryshop.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * It is used to set data and store
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
    private String pinCode;
    private String landMark;
}
