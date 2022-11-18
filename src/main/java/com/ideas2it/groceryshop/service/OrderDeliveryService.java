/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;

/**
 * <p>
 *    OrderDeliveryService interface contains all the method
 *    which will be implemented in OrderDeliveryServiceImpl
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
public interface OrderDeliveryService {

    /**
     * <p>
     *     Deliver person changes the delivery status once the product is delivered
     * </p>
     *
     * @param orderId - it contains order id for changing the delivery status
     * @return SuccessResponseDto - Order delivered successfully
     * @throws NotFoundException - if particular order is not found it shows No record found
     */
    SuccessResponseDto statusUpdate(Integer orderId) throws NotFoundException;

    /**
     * <p>
     *     This method is used for delivery person to pick order
     * </p>
     * @param orderId - to pick order by orderId
     * @return OrderDeliveryResponseDto - it show particular order which contains userId, orderId,
     *                                    totalPrice, totalQuantity, shippingAddress, orderStatus
     * @throws NotFoundException - if order not found for particular order id it shows No record found
     */
    OrderDeliveryResponseDto getDeliveryOrder(Integer orderId) throws NotFoundException;

}
