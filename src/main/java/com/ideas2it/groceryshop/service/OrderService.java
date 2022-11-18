/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.OrderDetailsResponseDto;
import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.OrderRequestDto;
import com.ideas2it.groceryshop.dto.OrderResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *    OrderService interface contains all the method which will be implemented in UserOrderServiceImpl
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
public interface OrderService {

    /**
     * <p>
     *     This method is used to place order by using cart
     * </p>
     *
     * @param orderRequest - it contains addressId which is used to place order for the given address
     * @return SuccessResponseDto - Order placed successfully
     * @throws NotFoundException - if cart is not found it shows Cart not found
     */
    SuccessResponseDto placeOrder(OrderRequestDto orderRequest) throws NotFoundException;

    /**
     * <p>
     *     This method is used to place order directly without adding it to cart by using current user
     * </p>
     * @param orderRequest - it contains quantity, productId, addressId
     *                          to place order directly without adding it to cart
     * @return SuccessResponseDto - Order placed successfully
     * @throws NotFoundException - if particular user is not found it shows User not found
     */
    SuccessResponseDto buyNow(OrderRequestDto orderRequest) throws NotFoundException;

    /**
     * <p>
     *     This method is useful for customer to retrieve Order by orderId
     * </p>
     *
     * @param orderId - with order id the order will be retrieved by customer
     * @return OrderResponseDto - it returns a particular order which contains userId,
     *                               orderedDate, expectedDeliveryDate, totalPrice,
     *                               totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - if order is not found it shows No record found
     */
    OrderResponseDto viewOrderById(Integer orderId) throws NotFoundException;

    /**
     * <p>
     *     This method is useful for Admin to retrieve all active orders
     * </p>
     * @return List<OrderResponseDto> - it returns list of active orders which contains userId,
     *                                     orderedDate, expectedDeliveryDate, totalPrice,
     *                                     totalQuantity, orderDetails, isDelivered for each order
     * @throws NotFoundException - if order is not placed it shows No record found exception
     */
    List<OrderResponseDto> viewAllActiveOrders() throws NotFoundException;

    /**
     * <p>
     *     This method is useful for Admin to view all cancelled orders
     * </p>
     *
     * @return List<UserOrderResponseDto> - it returns list of cancelled orders which contains userId,
     *                                     orderedDate, expectedDeliveryDate, totalPrice,
     *                                     totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - if cancelled orders is not found it shows No record found
     */

    List<OrderResponseDto> viewAllCancelledOrders() throws NotFoundException;

    /**
     * <p>
     *     This method is used to retrieve All orders of particular user
     * </p>
     *
     * @param userId - it helps the customer to view all the order placed
     * @return List<UserOrderResponseDto> - it returns a particular user order which contains userId,
     *                                      orderedDate, expectedDeliveryDate, totalPrice,
     *                                      totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - if order of particular user is not available it shows No record found
     */
    List<OrderResponseDto> viewOrderByUserId(Integer userId) throws NotFoundException;

    /**
     * <p>
     *     This method is useful for Customer to cancel order
     * </p>
     *
     * @param orderId - to cancel order by using order id
     * @return SuccessResponseDto - order cancelled successfully
     * @throws ExistedException - if order is already cancelled it shows Order already cancelled
     */
    SuccessResponseDto cancelOrderById(Integer orderId) throws ExistedException;

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
    List<OrderDetailsResponseDto> viewOrdersByProductId(Integer productId) throws NotFoundException;

    /**
     * <p>
     *     This method is useful for Admin to retrieve all orders by date
     * </p>
     * @param orderedDate - to view orders of particular date by using order date
     * @return List<UserOrderResponseDto> - it shows list of orders which contains userId,
     *                                     orderedDate, expectedDeliveryDate, totalPrice,
     *                                     totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - if order is not placed for mentioned date then it shows No record found
     */
    List<OrderResponseDto> viewOrdersByDate(Date orderedDate) throws NotFoundException;

    /**
     * <p>
     *     This method is used for admin to retrieve order of a particular user as
     *     per mentioned date and userId
     * </p>
     *
     * @param orderedDate to get order of particular date
     * @param userId - to get order of particular date of a user by using order date and user id
     * @return List<UserOrderResponseDto> - it shows list of orders which contains userId,
     *                                      orderedDate, expectedDeliveryDate, totalPrice,
     *                                      totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException if order is not placed on the date by particular user
     * then it shows No record found
     */
    List<OrderResponseDto> viewOrdersByIdAndDate(Date orderedDate, Integer userId) throws NotFoundException;

}
