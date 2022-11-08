package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeliveryRequestDto {
    private Boolean isDelivered;
    private Integer order_id;
    private Integer shippingAddressId;
}
