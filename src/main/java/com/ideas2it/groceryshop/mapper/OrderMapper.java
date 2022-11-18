/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.OrderResponseDto;
import com.ideas2it.groceryshop.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     It is used to convert entity to dto and vice versa
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
public class OrderMapper {

    /**
     * <p>
     *     This method is used to convert UserOrder to UserOrderResponseDto
     * </p>
     * @param order - it contains orderedDate, totalPrice, totalQuantity,
     *                  isDelivered, userId, orderDetails
     * @return UserOrderResponseDto - it contains orderedDate, totalPrice,
     *                               totalQuantity, isDelivered, userId, orderDetails
     */
     public static OrderResponseDto entityToDto(Order order) {
            OrderResponseDto userOrderResponseDto = new OrderResponseDto();
            userOrderResponseDto.setOrderedDate(order.getOrderedDate());
            userOrderResponseDto.setTotalPrice(order.getTotalPrice());
            userOrderResponseDto.setTotalQuantity(order.getTotalQuantity());
            userOrderResponseDto.setExpectedDeliveryDate(order.getOrderDelivery().getExpectedDeliveryDate());
            userOrderResponseDto.setIsDelivered(order.getOrderDelivery().getIsDelivered());
            userOrderResponseDto.setUserId(order.getUser().getId());
            userOrderResponseDto.setOrderDetails(OrderDetailsMapper.getAllOrdersEntityToDto(order.getOrderDetails()));
            return userOrderResponseDto;
        }

    /**
     * <p>
     *     This method is used to convert List<UserOrder> to List<UserOrderResponseDto>
     * </p>
     * @param orders - it contains orderedDate, totalPrice, totalQuantity,
     *                   isDelivered, userId, orderDetails
     * @return List<UserOrderResponseDto> - it contains orderedDate, totalPrice,
     *                                      totalQuantity, isDelivered, userId, orderDetails
     */
    public static List<OrderResponseDto> getAllOrdersDto(List<Order> orders) {
        List<OrderResponseDto> userOrderResponseDtos = new ArrayList<OrderResponseDto>();
        for(Order order: orders) {
            userOrderResponseDtos.add(entityToDto(order));
        }
        return userOrderResponseDtos;
    }

}
