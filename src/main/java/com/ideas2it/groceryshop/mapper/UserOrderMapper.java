package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.model.UserOrder;

import java.util.ArrayList;
import java.util.List;

public class UserOrderMapper {

    /**
     * This method is used to convert UserOrder to UserOrderResponseDto
     * @param userOrder
     * @return UserOrderResponseDto
     */
     public static UserOrderResponseDto entityToDto(UserOrder userOrder) {
            UserOrderResponseDto userOrderResponseDto = new UserOrderResponseDto();
            userOrderResponseDto.setOrderedDate(userOrder.getOrderedDate());
            userOrderResponseDto.setTotalPrice(userOrder.getTotalPrice());
            userOrderResponseDto.setIsDelivered(userOrder.getOrderDelivery().getIsDelivered());
            userOrderResponseDto.setUserId(userOrder.getUser().getId());
            userOrderResponseDto.setOrderDetails(OrderDetailsMapper.getAllOrdersEntityToDto(userOrder.getOrderDetails()));
            return userOrderResponseDto;
        }

    /**
     * This method is used to convert List<UserOrder> to List<UserOrderResponseDto>
     * @param userOrders
     * @return List<UserOrderResponseDto>
     */
    public static List<UserOrderResponseDto> getAllOrdersDto(List<UserOrder> userOrders) {
        List<UserOrderResponseDto> userOrderResponseDtos = new ArrayList<UserOrderResponseDto>();
        for(UserOrder userOrder: userOrders) {
            userOrderResponseDtos.add(entityToDto(userOrder));
        }
        return userOrderResponseDtos;
    }

}
