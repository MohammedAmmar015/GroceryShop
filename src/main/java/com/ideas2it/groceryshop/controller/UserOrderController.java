package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.request.UserOrderResponseDto;
import com.ideas2it.groceryshop.service.impl.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    @GetMapping("/")
    public List<UserOrderResponseDto> viewAllOrders() {
       return userOrderService.viewAllOrders();
    }





}
