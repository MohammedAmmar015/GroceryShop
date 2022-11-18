/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.OrderDeliveryService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *     Order Delivery Controller is used for delivery person to pick the order and change the status of order
 * </p>
 *
 * @author Dhanalakshmi M
 * @version 1.0
 * @since 18-11-2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderDeliveryController {
    private final OrderDeliveryService orderDeliveryService;

    private final Logger logger = LogManager.getLogger(OrderDeliveryController.class);

    /**
     * <p>
     *     This method is used for delivery person to pick order
     * </p>
     * @param orderId - to pick order by orderId
     * @return OrderDeliveryResponseDto - it show particular order which contains userId, orderId,
     *                                    totalPrice, totalQuantity, shippingAddress, orderStatus
     * @throws NotFoundException - if order not found for particular order id it shows No record found
     */
    @GetMapping("/{orderId}/orderDelivery")
    public OrderDeliveryResponseDto getDeliveryOrder(@PathVariable Integer orderId) throws NotFoundException {
        logger.debug("Entered getDeliveryOrder method in OrderController");
        return orderDeliveryService.getDeliveryOrder(orderId);
    }

    /**
     * <p>
     *     Deliver person changes the delivery status once the product is delivered
     * </p>
     *
     * @param orderId - it contains order id for changing the delivery status
     * @return SuccessResponseDto - Order delivered successfully
     * @throws NotFoundException - if order is not found it shows No record found
     */
    @PutMapping("/{orderId}/statusUpdate")
    public SuccessResponseDto statusUpdate(@PathVariable Integer orderId) throws NotFoundException {
        logger.debug("Entered statusUpdate method in OrderController");
        return orderDeliveryService.statusUpdate(orderId);
    }

}
