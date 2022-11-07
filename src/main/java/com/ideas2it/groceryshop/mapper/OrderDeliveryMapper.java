package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.OrderDetailsResponseDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.model.OrderDelivery;
import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.UserOrder;

import java.util.ArrayList;
import java.util.List;

public class OrderDeliveryMapper {

    public static OrderDeliveryResponseDto entityToDto(OrderDelivery orderDelivery){
        OrderDeliveryResponseDto orderDetailsResponseDto = new OrderDeliveryResponseDto();
        orderDetailsResponseDto.setDeliveryDate(orderDelivery.getDeliveryDate());
        orderDetailsResponseDto.setIsDelivered(orderDelivery.getIsDelivered());
        orderDetailsResponseDto.setShippingAddress(orderDelivery.getShippingAddress());
        orderDetailsResponseDto.setOrderId(orderDelivery.getUserOrder().getId());
        //orderDetailsResponseDto.setProduct(orderDetails.getProduct());
        return orderDetailsResponseDto;
    }

    public static List<OrderDeliveryResponseDto> getAllOrdersDto(List<OrderDelivery> orderDelivery) {
        List<OrderDeliveryResponseDto> orderDeliveryResponseDtos = new ArrayList<OrderDeliveryResponseDto>();
        for(OrderDelivery orderDeliver: orderDelivery) {
            orderDeliveryResponseDtos.add(entityToDto(orderDeliver));
        }
        return orderDeliveryResponseDtos;
    }


}
