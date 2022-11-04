package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.request.UserOrderResponseDto;

import java.util.List;

public interface UserOrderService {
    List<UserOrderResponseDto> viewAllOrders();

}
