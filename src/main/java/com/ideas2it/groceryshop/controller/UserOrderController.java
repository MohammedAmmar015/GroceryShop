package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.OrderDetailsResponseDto;
import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.UserOrderService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

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
     * This method is used to place order by cartId
     * @param userOrderRequest, cartId it contains quantity, productId, addressId, userId, cartId
     * @return Order placed successfully
     */
    @PostMapping("/placeOrder/{cartId}")
    public SuccessResponseDto placeOrder(@RequestBody UserOrderRequestDto userOrderRequest,
                                 @PathVariable Integer cartId) throws NotFound {
        return userOrderService.placeOrder(userOrderRequest, cartId);
    }

    /**
     * <p>
     *     This method is used to place order directly
     * </p>
     * @param userOrderRequest, userId it contains quantity, productId, addressId, userId
     * @return SuccessResponseDto Order placed successfully
     */
    @PostMapping("/buyNow/{userId}")
    public SuccessResponseDto buyNow(@RequestBody UserOrderRequestDto userOrderRequest,
                             @PathVariable Integer userId) throws NotFound {
        return userOrderService.buyNow(userOrderRequest, userId);
    }

    /**
     * To pick the order the status should be changed
     * @param orderId it contains order id
     * @return SuccessResponseDto Order placed successfully
     */
    @PutMapping("/statusUpdate/{orderId}")
    public SuccessResponseDto statusUpdate(@PathVariable Integer orderId) throws NotFound {
        return userOrderService.statusUpdate(orderId);
    }

    /**
     * <p>
     *     This method is used to retrieve all UserOrder in detail
     * </p>
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    @GetMapping("/activeOrders")
    public List<UserOrderResponseDto> viewAllActiveOrders() throws NotFound {
       return userOrderService.viewAllActiveOrders();
    }

    /**
     * <p>
     *     This method is used to view all cancelled orders
     * </p>
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    @GetMapping("/cancelledOrders")
    public List<UserOrderResponseDto> viewAllCancelledOrders() throws NotFound {
        return userOrderService.viewAllCancelledOrders();
    }

    /**
     * This method is used to retrieve UserOrder by orderId
     *
     * @param orderId it contains order id
     * @return UserOrderResponseDto it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    @GetMapping("/{orderId}")
    public UserOrderResponseDto viewOrderById(@PathVariable Integer orderId) throws NotFound {
        return userOrderService.viewOrderById(orderId);
    }

    /**
     * This method is used to retrieve All order by userId
     *
     * @param userId it contains user id
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    @GetMapping("/user/{userId}")
    public List<UserOrderResponseDto> viewOrderByUserId(@PathVariable Integer userId)
            throws NotFound {
        return userOrderService.viewOrderByUserId(userId);
    }

    /**
     * This method is used to cancel order by orderId
     *
     * @param orderId it contains order id
     * @return SuccessResponseDto order cancelled successfully
     * @throws NotFound Order not cancelled
     */
    @PutMapping("/cancelOrder/{orderId}")
    public SuccessResponseDto cancelOrder(@PathVariable() Integer orderId) throws NotFound, Existed {
        return userOrderService.cancelOrderById(orderId);
    }

    /**
     * This method is used to retrieve product by productId
     *
     * @param productId it contains productId
     * @return List<OrderDetailsResponseDto> it contains categoryName,
     * subCategoryName, productName, quantity, price
     * @throws NotFound No record found
     */
    @GetMapping("/products/{productId}")
    public List<OrderDetailsResponseDto> viewOrderByProductId(@PathVariable Integer productId)
            throws NotFound {
        return userOrderService.viewOrdersByProductId(productId);
    }

    /**
     * This method is used for delivery person to get order by orderId
     * @param orderId it contains orderId
     * @return OrderDeliveryResponseDto it contains userId, orderId,
     * totalPrice, totalQuantity, shippingAddress, orderStatus
     * @throws NotFound No record found
     */
    @GetMapping("/orderDelivery/{orderId}")
    public OrderDeliveryResponseDto getDeliveryOrder(@PathVariable Integer orderId) throws NotFound {
        return userOrderService.getDeliveryOrder(orderId);
    }

    /**
     * This method is used retrieve all orders by date
     * @param orderedDate it contains order date
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    @GetMapping("/date/{orderedDate}")
    public List<UserOrderResponseDto> viewOrdersByDate(@PathVariable String orderedDate)
            throws NotFound, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(orderedDate);
        return userOrderService.viewOrdersByDate(date);
    }

    /**
     * This method is used to retrieve orders by ordered date and userId
     * @param orderedDate it contains order Date
     * @param userId it contains user id
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    @GetMapping("/{orderedDate}/{userId}")
    public List<UserOrderResponseDto> viewOrdersByIdAndDate(@PathVariable String orderedDate,
                                                            @PathVariable Integer userId)
                                                            throws NotFound, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(orderedDate);
        return userOrderService.viewOrdersByIdAndDate(date, userId);
    }

}
