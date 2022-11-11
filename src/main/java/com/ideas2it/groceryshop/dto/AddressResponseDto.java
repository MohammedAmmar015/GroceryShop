package com.ideas2it.groceryshop.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * It is used to retrieve data from database
 *
 * @version 1.0 04-11-2022
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
    private Integer pinCode;
    private String landMark;
    private Date createdAt;
    private Date ModifiedAt;
    private Integer createdBy;
    private Integer modifiedBy;
    private Boolean isActive;
    private Boolean isDefault;
}
