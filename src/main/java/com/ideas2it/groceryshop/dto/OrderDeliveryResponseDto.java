package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     It is used to show response to the delivery person
 * </p>
 * @author Dhanalakshmi.M
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDeliveryResponseDto {

    private Integer userId;

    private Integer orderId;

    private Float totalPrice;

    private Integer totalQuantity;

    private AddressResponseDto shippingAddress;

    private Boolean orderStatus;

}

