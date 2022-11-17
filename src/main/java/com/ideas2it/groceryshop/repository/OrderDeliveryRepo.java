/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.OrderDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * <p>
 *     orderDelivery repository is used for doing CRUD operation of orderDelivery module
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 */
public interface OrderDeliveryRepo extends JpaRepository<OrderDelivery, Integer> {

    /**
     * <p>
     *     This method is used to retrieve order using orderId
     * </p>
     * @param OrderId - it contains order id
     * @return OrderDelivery - it contains isDelivered, deliveryDate,
     *                         expectedDeliveryDate, userOrder, shippingAddress
     */
    OrderDelivery findByUserOrderId(Integer OrderId);

    /**
     * <p>
     *     This method is used to make change the delivery status and delivery date
     * </p>
     * @param orderId - it contains order id
     * @return Integer - 0 or 1
     */
    @Modifying
    @Transactional
    @Query("Update OrderDelivery od set od.isDelivered = true, od.deliveryDate = CURRENT_TIMESTAMP where od.id = (select o.orderDelivery.id from UserOrder o where o.id = ?1)")
    Integer updateStatus(Integer orderId);

}
