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

import java.util.Date;
import java.util.List;

/**
 * <p>
 *     It is used to show order response dto which contains userId, orderedDate,
 *     expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
 * </p>
 *
 * @author Dhanalakshmi M
 * @version 1.0
 * @since 18-11-2022
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDto {

    private Integer userId;

    private Date orderedDate;

    private Date expectedDeliveryDate;

    private Float totalPrice;

    private Integer totalQuantity;

    private List<OrderDetailsResponseDto> orderDetails;

    private Boolean isDelivered;

}
