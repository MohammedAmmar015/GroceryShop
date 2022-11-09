package com.ideas2it.groceryshop.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.ideas2it.groceryshop.model.Address;
import com.ideas2it.groceryshop.model.Role;

/**
 *
 * It is used to set data from model object
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@Getter
@Setter
public class UserResponseDto {

    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private Long mobileNumber;
    private String email;
    private Date createdAt;
    private Date ModifiedAt;
    private Integer createdBy;
    private Integer modifiedBy;
    private Boolean isActive;
    private Role role;
    private List<Address> addresses;
}
