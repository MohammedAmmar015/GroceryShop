package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.UserOrder;

import java.util.ArrayList;
import java.util.List;

public class UserOrderMapper {

//    public static UserOrder dtoToEntity(UserOrderRequestDto userOrderRequestDto ) {
//        UserOrder userOrder = new UserOrder();
//        userOrder.setOrderDetails(userOrderRequestDto.getOrderDetails());
//        userOrder.setUser(userOrderRequestDto.getUser());
//        return userOrder;
//    }

        public static UserOrderResponseDto entityToDto(UserOrder userOrder) {
            UserOrderResponseDto userOrderResponseDto = new UserOrderResponseDto();
            userOrderResponseDto.setOrderedDate(userOrder.getOrderedDate());
            userOrderResponseDto.setTotalPrice(userOrder.getTotalPrice());
            userOrderResponseDto.setUserId(userOrder.getUser().getId());
            userOrderResponseDto.setOrderDetailsResponseDtos(OrderDetailsMapper.getAllOrdersEntityToDto(userOrder.getOrderDetails()));
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
