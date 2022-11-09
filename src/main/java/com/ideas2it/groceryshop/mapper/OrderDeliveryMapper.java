package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.model.OrderDelivery;
import java.util.ArrayList;
import java.util.List;

public class OrderDeliveryMapper {

    /**
     * This method is used to convert orderDelivery Entity to OrderDeliveryResponseDto
     * @param orderDelivery
     * @return OrderDeliveryResponseDto
     */

    public static OrderDeliveryResponseDto entityToDto(OrderDelivery orderDelivery) {
        OrderDeliveryResponseDto orderDeliveryResponseDto = new OrderDeliveryResponseDto();
        orderDeliveryResponseDto.setDeliveryDate(orderDelivery.getDeliveryDate());
        orderDeliveryResponseDto.setIsDelivered(orderDelivery.getIsDelivered());
        orderDeliveryResponseDto.setShippingAddress(AddressMapper.addressResponseDto(orderDelivery.getShippingAddress()));
        orderDeliveryResponseDto.setOrderId(orderDelivery.getUserOrder().getId());
        return orderDeliveryResponseDto;
    }

    /**
     * This method is used to convert List<OrderDelivery> to List<OrderDeliveryResponseDto>
     * @param orderDelivery
     * @return List<OrderDeliveryResponseDto>
     */
    public static List<OrderDeliveryResponseDto> getAllOrdersDto(List<OrderDelivery> orderDelivery) {
        List<OrderDeliveryResponseDto> orderDeliveryResponseDtos = new ArrayList<OrderDeliveryResponseDto>();
        for(OrderDelivery orderDeliver: orderDelivery) {
            orderDeliveryResponseDtos.add(entityToDto(orderDeliver));
        }
        return orderDeliveryResponseDtos;
    }

}
