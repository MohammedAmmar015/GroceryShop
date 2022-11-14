package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.model.OrderDelivery;

public class OrderDeliveryMapper {

    /**
     * This method is used to convert orderDelivery Entity to OrderDeliveryResponseDto
     * @param orderDelivery
     * @return OrderDeliveryResponseDto
     */
    public static OrderDeliveryResponseDto entityToDto(OrderDelivery orderDelivery) {
        OrderDeliveryResponseDto orderDeliveryResponseDto = new OrderDeliveryResponseDto();
        orderDeliveryResponseDto.setOrderId(orderDelivery.getUserOrder().getId());
        orderDeliveryResponseDto.setShippingAddress(AddressMapper.addressResponseDto(orderDelivery.getShippingAddress()));
        orderDeliveryResponseDto.setUserId(orderDelivery.getUserOrder().getUser().getId());
        orderDeliveryResponseDto.setOrderStatus(orderDelivery.getUserOrder().getIsActive());
        orderDeliveryResponseDto.setTotalPrice(orderDelivery.getUserOrder().getTotalPrice());
        return orderDeliveryResponseDto;
    }

}
