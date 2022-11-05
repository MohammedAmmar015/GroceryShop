package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.mapper.UserOrderMapper;
import com.ideas2it.groceryshop.model.UserOrder;
import com.ideas2it.groceryshop.repository.UserOrderRepo;
import com.ideas2it.groceryshop.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOrderServiceImpl implements UserOrderService {
    @Autowired
    UserOrderRepo userOrderRepo;
    public List<UserOrderResponseDto> viewAllOrders() {
        List<UserOrder> orders = userOrderRepo.findByIsActive(true);
        List<UserOrderResponseDto> viewPlacedOrders = UserOrderMapper.getAllOrdersDto(orders);
        return viewPlacedOrders;
    }


}
