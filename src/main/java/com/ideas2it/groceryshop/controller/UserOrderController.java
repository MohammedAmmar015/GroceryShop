package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
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
     * @param userOrderRequestDto, cart_id
     *
     */
    @PostMapping("/place-order/{cart_id}")
    public SuccessDto placeOrder(@RequestBody UserOrderRequestDto userOrderRequestDto, @PathVariable Integer cart_id) throws NotFound {
        return userOrderService.placeOrder(userOrderRequestDto, cart_id);
    }

    /**
     * <p>
     *     This method is used to place order directly
     * </p>
     * @param userOrderRequestDto, userId
     */
    @PostMapping("/buy-now/{userId}")
    public SuccessDto buyNow(@RequestBody UserOrderRequestDto userOrderRequestDto, @PathVariable Integer userId) throws NotFound {
        return userOrderService.buyNow(userOrderRequestDto, userId);
    }

    /**
     * <p>
     *     This method is used to retrieve all UserOrder in detail
     * </p>
     * @return List<UserOrderResponseDto>
     */
    @GetMapping("/all-orders")
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
     * @param order_id
     * @return UserOrderResponseDto
     * @throws NotFound
     */
    @GetMapping("/{order_id}")
    public UserOrderResponseDto viewOrderById(@PathVariable Integer order_id) throws NotFound {
        return userOrderService.viewOrderById(order_id);
    }

    /**
     * This method is used to retrieve All order by userId
     *
     * @param user_id
     * @return List<UserOrderResponseDto>
     * @throws NotFound
     */
    @GetMapping("/user-id/{user_id}")
    public List<UserOrderResponseDto> viewOrderByUserId(@PathVariable Integer user_id) throws NotFound {
        return userOrderService.viewOrderByUserId(user_id);
    }

    /**
     * This method is used to cancel order by orderId
     *
     * @param order_id
     * @return String
     * @throws NotFound
     */
    @PutMapping("/cancelOrder/{order_id}")
    public SuccessDto cancelOrder(@PathVariable Integer order_id) throws NotFound {
        return userOrderService.cancelOrderById(order_id);
    }

    /**
     * This method is used to retrieve product by productId
     *
     * @param product_id
     * @return List<UserOrderResponseDto>
     * @throws NotFound
     */
    @GetMapping("/product-id/{product_id}")
    public List<UserOrderResponseDto> viewOrderByProductId(@PathVariable Integer product_id) throws NotFound {
        return userOrderService.viewOrdersByProductId(product_id);
    }

    /**
     * This method is used for delivery person to get order by orderId
     * @param orderId
     * @return OrderDeliveryResponseDto
     * @throws NotFound
     */
    @GetMapping("/orders-delivery/{orderId}")
    public OrderDeliveryResponseDto getDeliveryOrder(@PathVariable Integer orderId) throws NotFound{
        return userOrderService.getDeliveryOrder(orderId);
    }

    /**
     * This method is used retrieve all orders by date
     * @param orderedDate
     * @return
     * @throws NotFound
     * @throws ParseException
     */
    @GetMapping("/orderedDate/{orderedDate}")
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
