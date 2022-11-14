package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.*;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
     * @param userOrderRequestDto, cartId
     *
     */
    @PostMapping("/placeOrder/{cartId}")
    public SuccessDto placeOrder(@RequestBody UserOrderRequestDto userOrderRequestDto, @PathVariable Integer cartId) throws NotFound {
        return userOrderService.placeOrder(userOrderRequestDto, cartId);
    }

    /**
     * <p>
     *     This method is used to place order directly
     * </p>
     * @param userOrderRequestDto, userId
     */
    @PostMapping("/buyNow/{userId}")
    public SuccessDto buyNow(@RequestBody UserOrderRequestDto userOrderRequestDto, @PathVariable Integer userId) throws NotFound {
        return userOrderService.buyNow(userOrderRequestDto, userId);
    }

    /**
     * To pick the order the status should be changed
     *
     */
    @PutMapping("/statusUpdate/{orderId}")
    public SuccessDto statusUpdate(@PathVariable Integer orderId) throws NotFound{
        return userOrderService.statusUpdate(orderId);
    }

    /**
     * <p>
     *     This method is used to retrieve all UserOrder in detail
     * </p>
     * @return List<UserOrderResponseDto>
     */
    @GetMapping("/activeOrders")
    public List<UserOrderResponseDto> viewAllActiveOrders() throws NotFound {
       return userOrderService.viewAllActiveOrders();
    }

    /**
     * <p>
     *     This method is used to view all cancelled orders
     * </p>
     * @return List<UserOrderResponseDto>
     */
    @GetMapping("/cancelledOrders")
    public List<UserOrderResponseDto> viewAllCancelledOrders() throws NotFound {
        return userOrderService.viewAllCancelledOrders();
    }

    /**
     * This method is used to retrieve UserOrder by orderId
     *
     * @param orderId
     * @return UserOrderResponseDto
     * @throws NotFound
     */
    @GetMapping("/{orderId}")
    public UserOrderResponseDto viewOrderById(@PathVariable Integer orderId) throws NotFound {
        return userOrderService.viewOrderById(orderId);
    }

    /**
     * This method is used to retrieve All order by userId
     *
     * @param userId
     * @return List<UserOrderResponseDto>
     * @throws NotFound
     */
    @GetMapping("/user/{userId}")
    public List<UserOrderResponseDto> viewOrderByUserId(@PathVariable Integer userId) throws NotFound {
        return userOrderService.viewOrderByUserId(userId);
    }

    /**
     * This method is used to cancel order by orderId
     *
     * @param orderId
     * @return String
     * @throws NotFound
     */
    @PutMapping("/cancelOrder/{orderId}")
    public SuccessDto cancelOrder(@PathVariable() Integer orderId) throws NotFound, Existed {
        return userOrderService.cancelOrderById(orderId);
    }

    /**
     * This method is used to retrieve product by productId
     *
     * @param productId
     * @return List<UserOrderResponseDto>
     * @throws NotFound
     */
    @GetMapping("/productId/{productId}")
    public List<OrderDetailsResponseDto> viewOrderByProductId(@PathVariable Integer productId) throws NotFound {
        return userOrderService.viewOrdersByProductId(productId);
    }

    /**
     * This method is used for delivery person to get order by orderId
     * @param orderId
     * @return OrderDeliveryResponseDto
     * @throws NotFound
     */
    @GetMapping("/orderDelivery/{orderId}")
    public OrderDeliveryResponseDto getDeliveryOrder(@PathVariable Integer orderId) throws NotFound {
        return userOrderService.getDeliveryOrder(orderId);
    }

    /**
     * This method is used retrieve all orders by date
     * @param orderedDate
     * @return
     * @throws NotFound
     * @throws ParseException
     */
    @GetMapping("/date/{orderedDate}")
    public List<UserOrderResponseDto> viewOrdersByDate(@PathVariable String orderedDate) throws NotFound, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(orderedDate);
        return userOrderService.viewOrdersByDate(date);
    }

    /**
     * This method is used to retrieve orders by ordered date and userId
     * @param orderedDate
     * @param userId
     * @return
     * @throws NotFound
     * @throws ParseException
     */
    @GetMapping("/{orderedDate}/{userId}")
    public List<UserOrderResponseDto> viewOrdersByIdAndDate(@PathVariable String orderedDate, @PathVariable Integer userId) throws NotFound, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(orderedDate);
        return userOrderService.viewOrdersByIdAndDate(date, userId);
    }

}
