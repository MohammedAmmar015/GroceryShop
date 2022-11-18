/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.*;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.impl.UserServiceImpl;
import com.ideas2it.groceryshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *     Order Controller is used to place by using cart and as well
 *     as direct buyNow option is applicable and we can also view
 *     the order by using the various options as per role
 * </p>
 *
 * @author Dhanalakshmi M
 * @version 1.0
 * @since 18-11-2022
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/orders")
public class OrderController {
    private final OrderService OrderService;
    private final UserServiceImpl userService;
    private final Logger logger = LogManager.getLogger(OrderController.class);

    /**
     * <p>
     *     This method is useful for placing order by using cart
     * </p>
     *
     * @param OrderRequest - it contains addressId which is used to place order for the given address
     * @return SuccessResponseDto - Order placed successfully
     * @throws NotFoundException - if cart is not found it shows Cart not found
     */
    @PostMapping("/placeOrder")
    public SuccessResponseDto placeOrder(@RequestBody OrderRequestDto OrderRequest) throws NotFoundException {
        logger.debug("Entered placeOrder method in OrderController");
        return OrderService.placeOrder(OrderRequest);
    }

    /**
     * <p>
     *     This method is used to place order directly without adding it to cart
     * </p>
     *
     * @param OrderRequest - it contains quantity, productId, addressId
     * @return SuccessResponseDto - Order placed successfully
     * @throws NotFoundException - if particular user is not found it shows User not found
     */
    @PostMapping("/buyNow")
    public SuccessResponseDto buyNow(@RequestBody OrderRequestDto OrderRequest
                             ) throws NotFoundException {
        Integer userId = userService.getCurrentUser().getId();
        logger.debug("Entered buyNow method in OrderController");
        return OrderService.buyNow(OrderRequest);
    }

    /**
     * <p>
     *     This method is useful for retrieving all active orders
     * </p>
     *
     * @return List<OrderResponseDto> - it returns list of active orders which contains userId,
     *                                  orderedDate, expectedDeliveryDate, totalPrice,
     *                                  totalQuantity, orderDetails, isDelivered for each order
     * @throws NotFoundException - if order is not found it shows No record found exception
     */
    @GetMapping("/activeOrders")
    public List<OrderResponseDto> viewAllActiveOrders() throws NotFoundException {
        logger.debug("Entered viewAllActiveOrders method in OrderController");
       return OrderService.viewAllActiveOrders();
    }

    /**
     * <p>
     *     This method is useful for Admin to view all cancelled orders
     * </p>
     *
     * @return List<OrderResponseDto> - it returns list of cancelled orders which contains userId,
     *                                  orderedDate, expectedDeliveryDate, totalPrice,
     *                                  totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - if cancelled orders is not found it shows No record found
     */
    @GetMapping("/cancelledOrders")
    public List<OrderResponseDto> viewAllCancelledOrders() throws NotFoundException {
        logger.debug("Entered viewAllCancelledOrders method in OrderController");
        return OrderService.viewAllCancelledOrders();
    }

    /**
     * <p>
     *     This method is useful for customer to retrieve Order by orderId
     * </p>
     *
     * @param orderId - with order id the order will be retrieved by customer
     * @return OrderResponseDto - it returns a particular order which contains userId,
     *                            orderedDate, expectedDeliveryDate, totalPrice,
     *                            totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - if order is not found it shows No record found
     */
    @GetMapping("/{orderId}")
    public OrderResponseDto viewOrderById(@PathVariable Integer orderId) throws NotFoundException {
        logger.debug("Entered viewOrderById method in OrderController");
        return OrderService.viewOrderById(orderId);
    }

    /**
     * <p>
     *     This method is used to retrieve All orders of particular user
     * </p>
     *
     * @return List<OrderResponseDto> - it returns a particular user order which contains userId,
     *                                  orderedDate, expectedDeliveryDate, totalPrice,
     *                                  totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - if order of particular user is not available it shows No record found
     */
    @GetMapping
    public List<OrderResponseDto> viewOrderByUserId() throws NotFoundException {
        Integer userId = userService.getCurrentUser().getId();
        logger.debug("Entered viewOrderByUserId method in OrderController");
        return OrderService.viewOrderByUserId(userId);
    }

    /**
     * <p>
     *     This method is useful for Customer to cancel order
     * </p>
     *
     * @param orderId - to cancel order by using order id
     * @return SuccessResponseDto - order cancelled successfully
     * @throws ExistedException - if order is already cancelled it shows Order already cancelled
     */
    @PutMapping("/{orderId}/cancelOrder")
    public SuccessResponseDto cancelOrder(@PathVariable() Integer orderId) throws ExistedException {
        logger.debug("Entered cancelOrder method in OrderController");
        return OrderService.cancelOrderById(orderId);
    }

    /**
     * <p>
     *     This method is useful for Admin to retrieve list of orders of particular product by productId
     * </p>
     *
     * @param productId - with product id we can retrieve the order placed for particular product
     * @return List<OrderDetailsResponseDto> - it contains list of orders of particular product
     *                                        which contains categoryName, subCategoryName,
     *                                        productName, quantity, price
     * @throws NotFoundException - if order of particular product not found it shows No record found
     */
    @GetMapping("/products/{productId}")
    public List<OrderDetailsResponseDto> viewOrderByProductId(@PathVariable Integer productId)
            throws NotFoundException {
        logger.debug("Entered viewOrderByProductId method in OrderController");
        return OrderService.viewOrdersByProductId(productId);
    }

    /**
     * <p>
     *     This method is useful for Admin to retrieve all orders by date
     * </p>
     * @param orderedDate - to view orders of particular date by using order date
     * @return List<OrderResponseDto> - it shows list of orders which contains userId,
     *                                     orderedDate, expectedDeliveryDate, totalPrice,
     *                                     totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - if order is not placed for mentioned date then it shows No record found
     * @throws ParseException - while formatting the date from string to date this exception occurs
     */
    @GetMapping("/date/{orderedDate}")
    public List<OrderResponseDto> viewOrdersByDate(@PathVariable String orderedDate)
            throws NotFoundException, ParseException {
        logger.debug("Entered viewOrdersByDate method in OrderController");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(orderedDate);
        return OrderService.viewOrdersByDate(date);
    }

    /**
     * <p>
     *     This method is used for admin to retrieve order of a particular user
     *     as per mentioned date and userId
     * </p>
     *
     * @param orderedDate
     * @param userId - to get order of particular date of a user by using order date and user id
     * @return List<OrderResponseDto> - it shows list of orders which contains userId,
     *                                  orderedDate, expectedDeliveryDate, totalPrice,
     *                                  totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException if order is not placed on the date by particular user then it shows No record
     * @throws ParseException while formatting the date from string to date this exception occurs
     */
    @GetMapping("/date/{orderedDate}/user/{userId}")
    public List<OrderResponseDto> viewOrdersByIdAndDate(@PathVariable String orderedDate,
                                                        @PathVariable Integer userId)
                                                            throws NotFoundException, ParseException {
        logger.debug("Entered viewOrdersByIdAndDate method in OrderController");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(orderedDate);
        return OrderService.viewOrdersByIdAndDate(date, userId);
    }

}
