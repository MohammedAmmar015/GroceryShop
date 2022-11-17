/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.OrderDetailsResponseDto;
import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *     UserOrderService interface contains all the method which will be implemented in UserOrderService
 * </p>
 * @author Dhanalakshmi.M
 * @version 1.0
 */
public interface UserOrderService {

    /**
     * <p>
     *     This method is useful for Admin to retrieve all active orders
     * </p>
     * @return List<UserOrderResponseDto> - it returns list of active orders which contains userId,
     *                                     orderedDate, expectedDeliveryDate, totalPrice,
     *                                     totalQuantity, orderDetails, isDelivered for each order
     * @throws NotFound - if order is not placed it shows No record found exception
     */
    List<UserOrderResponseDto> viewAllActiveOrders() throws NotFound;

    /**
     * <p>
     *     This method is useful for Admin to view all cancelled orders
     * </p>
     *
     * @return List<UserOrderResponseDto> - it returns list of cancelled orders which contains userId,
     *                                     orderedDate, expectedDeliveryDate, totalPrice,
     *                                     totalQuantity, orderDetails, isDelivered
     * @throws NotFound - if cancelled orders is not found it shows No record found
     */

    List<UserOrderResponseDto> viewAllCancelledOrders() throws NotFound;

    /**
     * <p>
     *     This method is useful for customer to retrieve Order by orderId
     * </p>
     *
     * @param orderId - with order id the order will be retrieved by customer
     * @return UserOrderResponseDto - it returns a particular order which contains userId,
     *                               orderedDate, expectedDeliveryDate, totalPrice,
     *                               totalQuantity, orderDetails, isDelivered
     * @throws NotFound - if order is not found it shows No record found
     */
    UserOrderResponseDto viewOrderById(Integer orderId) throws NotFound;

    /**
     * <p>
     *     This method is used to place order by using cart
     * </p>
     *
     * @param userOrderRequest - it contains addressId which is used to place order for the given address
     * @return SuccessResponseDto - Order placed successfully
     * @throws NotFound - if cart is not found it shows Cart not found
     */
    SuccessResponseDto placeOrder(UserOrderRequestDto userOrderRequest) throws NotFound;

    /**
     * <p>
     *     Deliver person changes the delivery status once the product is delivered
     * </p>
     *
     * @param orderId - it contains order id for changing the delivery status
     * @return SuccessResponseDto - Order delivered successfully
     * @throws NotFound - if particular order is not found it shows No record found
     */
    SuccessResponseDto statusUpdate(Integer orderId) throws NotFound;

    /**
     * <p>
     *     This method is used to place order directly without adding it to cart by using current user
     * </p>
     * @param userOrderRequest - it contains quantity, productId, addressId
     *                          to place order directly without adding it to cart
     * @return SuccessResponseDto - Order placed successfully
     * @throws NotFound - if particular user is not found it shows User not found
     */
    SuccessResponseDto buyNow(UserOrderRequestDto userOrderRequest) throws NotFound;

    /**
     * <p>
     *     This method is useful for Admin to retrieve list of orders of particular product by productId
     * </p>
     *
     * @param productId - with product id we can retrieve the order placed for particular product
     * @return List<OrderDetailsResponseDto> - it contains list of orders of particular product
     *                                        which contains categoryName, subCategoryName,
     *                                        productName, quantity, price
     * @throws NotFound - if order of particular product not found it shows No record found
     */
    List<OrderDetailsResponseDto> viewOrdersByProductId(Integer productId) throws NotFound;

    /**
     * <p>
     *     This method is used to retrieve All orders of particular user
     * </p>
     *
     * @param userId - it helps the customer to view all the order placed
     * @return List<UserOrderResponseDto> - it returns a particular user order which contains userId,
     *                                      orderedDate, expectedDeliveryDate, totalPrice,
     *                                      totalQuantity, orderDetails, isDelivered
     * @throws NotFound - if order of particular user is not available it shows No record found
     */
    List<UserOrderResponseDto> viewOrderByUserId(Integer userId) throws NotFound;

    /**
     * <p>
     *     This method is useful for Customer to cancel order
     * </p>
     *
     * @param orderId - to cancel order by using order id
     * @return SuccessResponseDto - order cancelled successfully
     * @throws NotFound - if order is not found it shows Order not found
     * @throws Existed - if order is already cancelled it shows Order already cancelled
     */
    SuccessResponseDto cancelOrderById(Integer orderId) throws NotFound, Existed;

    /**
     * <p>
     *     This method is used for delivery person to pick order
     * </p>
     * @param orderId - to pick order by orderId
     * @return OrderDeliveryResponseDto - it show particular order which contains userId, orderId,
     *                                    totalPrice, totalQuantity, shippingAddress, orderStatus
     * @throws NotFound - if order not found for particular order id it shows No record found
     */
    OrderDeliveryResponseDto getDeliveryOrder(Integer orderId) throws NotFound;

    /**
     * <p>
     *     This method is useful for Admin to retrieve all orders by date
     * </p>
     * @param orderedDate - to view orders of particular date by using order date
     * @return List<UserOrderResponseDto> - it shows list of orders which contains userId,
     *                                     orderedDate, expectedDeliveryDate, totalPrice,
     *                                     totalQuantity, orderDetails, isDelivered
     * @throws NotFound - if order is not placed for mentioned date then it shows No record found
     */
    List<UserOrderResponseDto> viewOrdersByDate(Date orderedDate) throws NotFound;

    /**
     * <p>
     *     This method is used for admin to retrieve order of a particular user as per mentioned date and userId
     * </p>
     *
     * @param orderedDate
     * @param userId - to get order of particular date of a user by using order date and user id
     * @return List<UserOrderResponseDto> - it shows list of orders which contains userId,
     *                                      orderedDate, expectedDeliveryDate, totalPrice,
     *                                      totalQuantity, orderDetails, isDelivered
     * @throws NotFound if order is not placed on the date by particular user then it shows No record found
     */
    List<UserOrderResponseDto> viewOrdersByIdAndDate(Date orderedDate, Integer userId) throws NotFound;

}
