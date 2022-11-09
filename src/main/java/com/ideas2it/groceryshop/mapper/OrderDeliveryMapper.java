package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.model.OrderDelivery;
import java.util.ArrayList;
import java.util.List;

public class OrderDeliveryMapper {

    public static OrderDeliveryResponseDto entityToDto(OrderDelivery orderDelivery){
        OrderDeliveryResponseDto orderDeliveryResponseDto = new OrderDeliveryResponseDto();
        orderDeliveryResponseDto.setDeliveryDate(orderDelivery.getDeliveryDate());
        orderDeliveryResponseDto.setIsDelivered(orderDelivery.getIsDelivered());
        orderDeliveryResponseDto.setShippingAddress(AddressMapper.addressResponseDto(orderDelivery.getShippingAddress()));
        orderDeliveryResponseDto.setOrderId(orderDelivery.getUserOrder().getId());
        return orderDeliveryResponseDto;
    }

    public static List<OrderDeliveryResponseDto> getAllOrdersDto(List<OrderDelivery> orderDelivery) {
        List<OrderDeliveryResponseDto> orderDeliveryResponseDtos = new ArrayList<OrderDeliveryResponseDto>();
        for(OrderDelivery orderDeliver: orderDelivery) {
            orderDeliveryResponseDtos.add(entityToDto(orderDeliver));
        }
        return orderDeliveryResponseDtos;
    }


}
