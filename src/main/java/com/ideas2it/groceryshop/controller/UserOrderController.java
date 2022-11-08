package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.model.UserOrder;
import com.ideas2it.groceryshop.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class UserOrderController {


    private final UserOrderService userOrderService;

    @PostMapping("/place-order/{cart_id}")
    public void placeOrder(@PathVariable int cart_id) {
        userOrderService.placeOrder(cart_id);
    }

    @PostMapping("/buy-now/{userId}")
    public void placeOrder(@RequestBody UserOrderRequestDto userOrderRequestDto, @PathVariable Integer userId) {
        userOrderService.buyNow(userOrderRequestDto, userId);
    }

    @GetMapping
    public List<UserOrderResponseDto> viewAllActiveOrders() {
       return userOrderService.viewAllActiveOrders();
    }

    @GetMapping("/cancelledOrders")
    public List<UserOrderResponseDto> viewAllCancelledOrders() {
        return userOrderService.viewAllCancelledOrders();
    }
    @GetMapping("/{order_id}")
    public UserOrderResponseDto viewOrderById(@PathVariable Integer order_id) {
        return userOrderService.viewOrderById(order_id);
    }

    @GetMapping("/user-id/{user_id}")
    public List<UserOrderResponseDto> viewOrderByUserId(@PathVariable Integer user_id) {
        return userOrderService.viewOrderByUserId(user_id);
    }

    @PutMapping("/cancelOrder/{order_id}")
    public String cancelOrder(@PathVariable Integer order_id) {
        return userOrderService.cancelOrderById(order_id);
    }

    @GetMapping("/product-id/{product_id}")
    public void viewOrderByProductId(@PathVariable Integer product_id) {
        userOrderService.viewOrdersByProductId(product_id);
    }

    @GetMapping("/orders-delivery/{orderId}")
    public List<OrderDeliveryResponseDto> deliverOrder(@PathVariable Integer orderId) {
        return userOrderService.pendingOrder(orderId);
    }

//    @GetMapping("/orderedDate")
//    public List<UserOrderResponseDto> viewOrderByProductId(@RequestParam Date orderedDate) {
//        return userOrderService.viewOrdersByDate(orderedDate);
//    }





}
