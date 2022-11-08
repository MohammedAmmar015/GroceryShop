package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderRequestDto {
    private Integer quantity;
    private Integer productId;
    private Integer addressId;
    private Integer cartId;
}
