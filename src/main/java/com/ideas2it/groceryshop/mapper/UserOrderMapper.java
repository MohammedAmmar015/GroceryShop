package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.request.UserOrderResponseDto;
import com.ideas2it.groceryshop.model.UserOrder;

import java.util.ArrayList;
import java.util.List;

public class UserOrderMapper {

//    public static UserOrder dtoToEntity(UserOrderRequestDto userOrderRequestDto ) {
//        UserOrder userOrder = new UserOrder();
//        userOrder.setId(userOrderRequestDto.getId());
//        userOrder.setTotalPrice(userOrderRequestDto.getTotalPrice());
//        userOrder.setIsActive(userOrderRequestDto.getIsActive());
//        userOrder.setOrderDetails(userOrderRequestDto.getOrderDetails());
//        userOrder.setCart(userOrderRequestDto.getCart());
//        userOrder.setUser(userOrderRequestDto.getUser());
//        return userOrder;
//    }

        public static UserOrderResponseDto entityToDto(UserOrder userOrder) {
            UserOrderResponseDto userOrderResponseDto = new UserOrderResponseDto();
            userOrderResponseDto.setOrderedDate(userOrder.getOrderedDate());
            userOrderResponseDto.setTotalPrice(userOrder.getTotalPrice());
            userOrderResponseDto.setOrderDetailsResponseDtos(OrderDetailsMapper.getAllOrdersEntityToDto(userOrder.getOrderDetails()));
            userOrderResponseDto.setUser(userOrder.getUser());
            return userOrderResponseDto;
        }

    public static List<UserOrderResponseDto> getAllOrdersDto(List<UserOrder> userOrders) {
        List<UserOrderResponseDto> userOrderResponseDtos = new ArrayList<UserOrderResponseDto>();
        for(UserOrder userOrder: userOrders) {
            userOrderResponseDtos.add(entityToDto(userOrder));
        }
        return userOrderResponseDtos;
    }


}
