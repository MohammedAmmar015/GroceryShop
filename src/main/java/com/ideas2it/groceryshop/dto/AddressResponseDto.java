package com.ideas2it.groceryshop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * It is used to retrieve data from database
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@Getter
@Setter
public class AddressResponseDto {

    private Integer id;
    private String street;
    private String area;
    private String pinCode;
    private String landMark;
    private Date createdAt;
    private Date ModifiedAt;
    private Integer createdBy = 1;
    private Integer modifiedBy = 1;
    private Boolean isActive = Boolean.TRUE;
    private Boolean isDefault = Boolean.TRUE;
}
