package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * It is used to get request from the delivery person
 * @author Dhanalakshmi.M
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeliveryRequestDto {

    private Boolean isDelivered;

    private Integer order_id;

    private Integer shippingAddressId;

}
