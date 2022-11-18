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
 *     orderDelivery repository is used for storing and retrieving the order delivery related
 *     data into the order_delivery table and it also helps the service to communicate with database
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
public interface OrderDeliveryRepository extends JpaRepository<OrderDelivery, Integer> {

    /**
     * <p>
     *     This method is used to retrieve order using orderId
     * </p>
     * @param OrderId - it contains order id
     * @return OrderDelivery - it contains isDelivered, deliveryDate,
     *                         expectedDeliveryDate, userOrder, shippingAddress
     */
    OrderDelivery findByOrderId(Integer OrderId);

    /**
     * <p>
     *     This method is used to make change the delivery status and delivery date
     * </p>
     * @param orderId - it contains order id
     */
    @Modifying
    @Transactional
    @Query("Update OrderDelivery od set od.isDelivered = true, od.deliveryDate = CURRENT_TIMESTAMP " +
            "where od.id = (select o.orderDelivery.id from UserOrder o where o.id = ?1)")
    void updateStatus(Integer orderId);

}
