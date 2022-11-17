package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * It is a user order request dto which is used for getting input from the user
 * @author DhanaLakshmi.M
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderRequestDto {

    private Integer quantity;

    private Integer productId;

    private Integer addressId;

}
