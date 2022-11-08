package com.ideas2it.groceryshop.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * It is used to update role name
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@Getter
@Setter
public class UpdateRoleRequestDto {

    private String nameToUpdate;
    private String name;
}
