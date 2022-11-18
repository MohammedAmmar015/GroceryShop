/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     It is used to get request from the delivery person in the form of dto object
 *     which contains isDelivered, orderId, shippingAddressId
 * </p>
 *
 * @author Dhanalakshmi M
 * @version 1.0
 * @since 18-11-2022
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
