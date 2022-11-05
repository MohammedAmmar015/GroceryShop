package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.UserOrderResponseDto;

import java.util.List;

public interface UserOrderService {
    List<UserOrderResponseDto> viewAllActiveOrders();
    List<UserOrderResponseDto> viewAllCancelledOrders();

    UserOrderResponseDto viewOrderById(Integer orderId);
    void placeOrder(Integer id);

}
