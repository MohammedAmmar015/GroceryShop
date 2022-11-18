/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.model.OrderDelivery;

/**
 * <p>
 *     It is used to convert entity to dto
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
public class OrderDeliveryMapper {

    /**
     * <p>
     *     This method is used to convert orderDelivery Entity to OrderDeliveryResponseDto
     * </p>
     * @param orderDelivery - it contains userId, orderId, shippingAddress, orderStatus, totalPrice, totalQuantity
     * @return OrderDeliveryResponseDto - userId, orderId, totalPrice, shippingAddress, orderStatus, totalQuantity
     */
    public static OrderDeliveryResponseDto entityToDto(OrderDelivery orderDelivery) {
        OrderDeliveryResponseDto orderDeliveryResponse = new OrderDeliveryResponseDto();
        orderDeliveryResponse.setOrderId(orderDelivery.getOrder().getId());
        orderDeliveryResponse.setShippingAddress(AddressMapper.addressResponseDto
                (orderDelivery.getShippingAddress()));
        orderDeliveryResponse.setUserId(orderDelivery.getOrder().getUser().getId());
        orderDeliveryResponse.setOrderStatus(orderDelivery.getOrder().getIsActive());
        orderDeliveryResponse.setTotalPrice(orderDelivery.getOrder().getTotalPrice());
        orderDeliveryResponse.setTotalQuantity(orderDelivery.getOrder().getTotalQuantity());
        return orderDeliveryResponse;
    }

}
