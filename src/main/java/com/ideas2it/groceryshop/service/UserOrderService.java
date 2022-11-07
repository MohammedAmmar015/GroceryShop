package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;

import java.util.Date;
import java.util.List;

public interface UserOrderService {
    List<UserOrderResponseDto> viewAllActiveOrders();
    List<UserOrderResponseDto> viewAllCancelledOrders();
    UserOrderResponseDto viewOrderById(Integer orderId);
    void placeOrder(Integer id);
    void buyNow(UserOrderRequestDto userOrderRequestDto, Integer userId);
    List<UserOrderResponseDto> viewOrdersByProductId(Integer productId);

    List<UserOrderResponseDto> viewOrderByUserId(Integer user_id);

//    List<UserOrderResponseDto> viewOrdersByDate(UserOrderRequestDto orderedDate);

    String cancelOrderById(Integer user_id);

    List<OrderDeliveryResponseDto> pendingOrder(Integer orderId);
//    void viewAllProductOrders(Integer productId);

}
