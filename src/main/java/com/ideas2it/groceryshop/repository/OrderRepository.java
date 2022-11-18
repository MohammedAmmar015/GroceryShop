/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *     order repository is used for storing and retrieving the order related
 *     data into the order_item table and it also helps the service to communicate with database.
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /**
     * <p>
     *     This method is used to retrieve all the active orders
     * </p>
     *
     * @param status - it contains true which denotes all active orders
     * @return List<Order> - it returns list of order which contains orderedDate, totalPrice,
     *                           totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    List<Order> findByIsActive(Boolean status);
    /**
     * <p>
     *     This method is used to retrieve list of orders using user id
     * </p>
     *
     * @param userId - to get list of orders with user id
     * @return List<Order> - it returns list of order which contains orderedDate, totalPrice,
     *                          totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    List<Order> findByUserId(Integer userId);

    /**
     * <p>
     *     This method is used to cancel the order
     * </p>
     *
     * @param orderId - cancel order with order id
     * @return Integer - it returns 1 if order is cancelled successfully
     *                 and returns 0 if order is not cancelled
     */
    @Modifying
    @Transactional
    @Query(value = "update Order o set o.isActive = false where o.id = ?1 and o.user.id = ?2")
    Integer cancelOrderById(Integer orderId, Integer userId);

    /**
     * <p>
     *     This method is used to get order by using orderId and userId as parameter
     * </p>
     * @param orderId - it gets the order by using orderId
     * @param userId - it gets the order by using orderId and userId
     * @return Order - it returns the user order which contains orderedDate, totalPrice,
     *                   totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    Optional<Order> findByIdAndUserId(Integer orderId, Integer userId);

    /**
     * <p>
     *     This method is used to get the user order by orderId, isActive, userId and isDelivered
     * </p>
     * @param orderId - this used get order by checking orderId
     * @param isActive - this used get order by checking isActive
     * @param userId - this used get order by checking userId
     * @param isDelivered - this used get order by checking orderId, isActive, userId and isDelivered
     * @return Order - it return the UserOrder which contains orderedDate, totalPrice,
     *                   totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    Optional<Order> findByIdAndIsActiveAndUserIdAndOrderDeliveryIsDelivered(Integer orderId,
                                                                                Boolean isActive,
                                                                                Integer userId,
                                                                                Boolean isDelivered);

    /**
     * <p>
     *     This method is used to retrieve list of order details which is of particular product
     * </p>
     *
     * @param productId - to get list of order details by product id
     * @return List<OrderDetails> - It returns list of order details which contains quantity, price, product
     */
    @Query(value = "Select o from OrderDetails o where o.product.id = ?1")
    List<OrderDetails> findByProductId(Integer productId);

    /**
     * <p>
     *     This method is used to retrieve order of particular date
     * </p>
     *
     * @param orderedDate - to get list of orders of a particular date using ordered date
     * @return List<UserOrder> - It returns list of orders which contains orderedDate, totalPrice,
     *                          totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    @Query("Select o from Order o where date(o.orderedDate) = ?1")
    List<Order> findByOrderedDate(Date orderedDate);

    /**
     * <p>
     *     This method is used to retrieve order by using userId and orderedDate
     * </p>
     *
     * @param orderedDate - to get list of orders of particular user on particular date
     * @param userId - to get list of orders of particular user on particular date
     *                by using order date and userId
     * @return List<UserOrder> - to get list of orders which contains orderedDate, totalPrice,
     *                          totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    @Query("Select o from Order o where Date(o.orderedDate) = ?1 AND o.user.id = ?2")
    List<Order> findByOrderedDateAndUserId(Date orderedDate, Integer userId);

}
