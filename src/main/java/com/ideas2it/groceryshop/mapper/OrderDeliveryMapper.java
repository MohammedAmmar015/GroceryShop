package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.model.OrderDelivery;

/**
 * It is used to convert entity to dto
 * @author Dhanalakshmi.M
 * @version 1.0
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
        orderDeliveryResponse.setOrderId(orderDelivery.getUserOrder().getId());
        orderDeliveryResponse.setShippingAddress(AddressMapper.addressResponseDto
                (orderDelivery.getShippingAddress()));
        orderDeliveryResponse.setUserId(orderDelivery.getUserOrder().getUser().getId());
        orderDeliveryResponse.setOrderStatus(orderDelivery.getUserOrder().getIsActive());
        orderDeliveryResponse.setTotalPrice(orderDelivery.getUserOrder().getTotalPrice());
        orderDeliveryResponse.setTotalQuantity(orderDelivery.getUserOrder().getTotalQuantity());
        return orderDeliveryResponse;
    }

}
