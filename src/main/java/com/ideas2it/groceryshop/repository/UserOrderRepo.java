/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *     Order repository is used for doing CRUD of order module
 * </p>
 * @author Dhanalakshmi.M
 * @version 1.0
 */
public interface UserOrderRepo extends JpaRepository<UserOrder, Integer> {

    /**
     * <p>
     *     This method is used to retrieve all the active orders
     * </p>
     *
     * @param status - it contains true which denotes all active orders
     * @return List<UserOrder> - it returns list of order which contains orderedDate, totalPrice,
     *                           totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    List<UserOrder> findByIsActive(Boolean status);
    /**
     * <p>
     *     This method is used to retrieve list of orders using user id
     * </p>
     *
     * @param userId - to get list of orders with user id
     * @return List<UserOrder> - it returns list of order which contains orderedDate, totalPrice,
     *                          totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    List<UserOrder> findByUserId(Integer userId);

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
    @Query(value = "update UserOrder o set o.isActive = false where o.id = ?1 and o.user.id = ?2")
    Integer cancelOrderById(Integer orderId, Integer userId);

    /**
     * <p>
     *     This method is used to get order by using orderId and userId as parameter
     * </p>
     * @param orderId
     * @param userId - it gets the order by using orderId and userId
     * @return UserOrder - it returns the user order which contains orderedDate, totalPrice,
     *                   totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    Optional<UserOrder> findByIdAndUserId(Integer orderId, Integer userId);

    /**
     * <p>
     *     This method is used to get the user order by orderId, isActive, userId and isDelivered
     * </p>
     * @param orderId
     * @param isActive
     * @param userId
     * @param isDelivered - this used get order by checking orderId, isActive, userId and isDelivered
     * @return UserOrder - it return the UserOrder which contains orderedDate, totalPrice,
     *                   totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    Optional<UserOrder> findByIdAndIsActiveAndUserIdAndOrderDeliveryIsDelivered(Integer orderId, Boolean isActive, Integer userId, Boolean isDelivered);

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
    @Query("Select o from UserOrder o where date(o.orderedDate) = ?1")
    List<UserOrder> findByOrderedDate(Date orderedDate);

    /**
     * <p>
     *     This method is used to retrieve order by using userId and orderedDate
     * </p>
     *
     * @param orderedDate
     * @param userId - to get list of orders of particular user on particular date
     *                by using order date and userId
     * @return List<UserOrder> - to get list of orders which contains orderedDate, totalPrice,
     *                          totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    @Query("Select o from UserOrder o where Date(o.orderedDate) = ?1 AND o.user.id = ?2")
    List<UserOrder> findByOrderedDateAndUserId(Date orderedDate, Integer userId);

}
