/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.groceryshop.dto.OrderResponseDto;
import com.ideas2it.groceryshop.model.Order;


/**
 * <p>
 *     Converts entity to dto and vice versa
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
public class OrderMapper {

    /**
     * <p>
     *     Converts Order to OrderResponseDto
     * </p>
     *
     * @param order - contains orderedDate, totalPrice, totalQuantity,
     *                isDelivered, userId, orderDetails
     * @return OrderResponseDto - contains orderedDate, totalPrice,
     *                            totalQuantity, isDelivered, userId, orderDetails
     */
     public static OrderResponseDto toOrderResponseDto(Order order) {
         OrderResponseDto orderResponseDto = new OrderResponseDto();
         orderResponseDto.setOrderedDate(order.getOrderedDate());
         orderResponseDto.setTotalPrice(order.getTotalPrice());
         orderResponseDto.setTotalQuantity(order.getTotalQuantity());
         orderResponseDto.setExpectedDeliveryDate(order.getOrderDelivery().getExpectedDeliveryDate());
         orderResponseDto.setIsDelivered(order.getOrderDelivery().getIsDelivered());
         orderResponseDto.setUserId(order.getUser().getId());
         orderResponseDto.setOrderDetails(OrderDetailsMapper.toOrderDetailsDtoList
                                         (order.getOrderDetails()));
         return orderResponseDto;
        }

    /**
     * <p>
     *     Converts Order to OrderResponseDto
     * </p>
     *
     * @param orders - contains list of orders which contains orderedDate, totalPrice, totalQuantity,
     *                 isDelivered, userId, orderDetails
     * @return OrderResponseDto - contains list of OrderResponseDto which contains orderedDate, totalPrice,
     *                            totalQuantity, isDelivered, userId, orderDetails
     */
    public static List<OrderResponseDto> toOrdersDtoList(List<Order> orders) {
        List<OrderResponseDto> orderResponses = new ArrayList<>();
        for(Order order: orders) {
            orderResponses.add(toOrderResponseDto(order));
        }
        return orderResponses;
    }
}
