package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.model.UserOrder;
import com.ideas2it.groceryshop.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class UserOrderController {

    private final UserOrderService userOrderService;

    @GetMapping
    public List<UserOrderResponseDto> viewAllActiveOrders() {
       return userOrderService.viewAllActiveOrders();
    }

    @PostMapping("cart/{cart_id}")
    public void placeOrder(@PathVariable int cart_id) {
        userOrderService.placeOrder(cart_id);
    }

    @GetMapping("/cancelledOrders")
    public List<UserOrderResponseDto> viewAllCancelledOrders() {
        return userOrderService.viewAllCancelledOrders();
    }
    @GetMapping("/{order_id}")
    public UserOrderResponseDto viewOrderById(@PathVariable int order_id) {
        return userOrderService.viewOrderById(order_id);
    }

//    @GetMapping("/{product_id}")
//    public void viewOrderByProductId(@PathVariable int product_id) {
//        userOrderService.viewAllProductOrders(product_id);
//    }





}
