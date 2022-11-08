package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     USerOrder Controller is used to place and cancel order
 *     And we can also view the order by filter options
 * </p>
 *
 * @author Dhanalakshmi
 * @version 1.0V
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class UserOrderController {


    private final UserOrderService userOrderService;

    /**
     * <p>
     *     This method is used to place order by cartId
     * </p>
     * @param userOrderRequestDto, cart_id
     *
     */
    @PostMapping("/place-order/{cart_id}")
    public void placeOrder(@RequestBody UserOrderRequestDto userOrderRequestDto, @PathVariable Integer cart_id) {
        userOrderService.placeOrder(userOrderRequestDto, cart_id);
    }

    /**
     * <p>
     *     This method is used to place order directly
     * </p>
     * @param userOrderRequestDto, userId
     */
    @PostMapping("/buy-now/{userId}")
    public void buyNow(@RequestBody UserOrderRequestDto userOrderRequestDto, @PathVariable Integer userId) {
        userOrderService.buyNow(userOrderRequestDto, userId);
    }

    /**
     * <p>
     *     This method is used to retrieve all UserOrder in detail
     * </p>
     * @return List<UserOrderResponseDto>
     */
    @GetMapping("/all-orders")
    public List<UserOrderResponseDto> viewAllActiveOrders() {
       return userOrderService.viewAllActiveOrders();
    }

    /**
     * <p>
     *     This method is used to view all cancelled orders
     * </p>
     * @return List<UserOrderResponseDto>
     */
    @GetMapping("/cancelledOrders")
    public List<UserOrderResponseDto> viewAllCancelledOrders() {
        return userOrderService.viewAllCancelledOrders();
    }

    /**
     * <p>
     *     This method is used to retrieve UserOrder by orderId
     * </p>
     * @param order_id
     * @return UserOrderResponseDto
     */
    @GetMapping("/{order_id}")
    public UserOrderResponseDto viewOrderById(@PathVariable Integer order_id) {
        return userOrderService.viewOrderById(order_id);
    }

    /**
     * <p>
     *     This method is used to retrieve All order by userId
     * </p>
     * @param user_id
     * @return List<UserOrderResponseDto>
     */
    @GetMapping("/user-id/{user_id}")
    public List<UserOrderResponseDto> viewOrderByUserId(@PathVariable Integer user_id) {
        return userOrderService.viewOrderByUserId(user_id);
    }

    /**
     * <p>
     *     This method is used to cancel order by orderId
     * </p>
     * @param order_id
     * @return String
     */
    @PutMapping("/cancelOrder/{order_id}")
    public String cancelOrder(@PathVariable Integer order_id) {
        return userOrderService.cancelOrderById(order_id);
    }

    /**
     * <p>
     *     This method is used to retrieve product by productId
     * </p>
     * @param product_id
     * @return List<UserOrderResponseDto>
     */
    @GetMapping("/product-id/{product_id}")
    public List<UserOrderResponseDto> viewOrderByProductId(@PathVariable Integer product_id) {
        return userOrderService.viewOrdersByProductId(product_id);
    }

}
