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

public interface UserOrderService {

    /**
     * This method is used to retrieve all active orders
     *
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    List<UserOrderResponseDto> viewAllActiveOrders() throws NotFound;

    /**
     * This method is used to retrieve all the cancelled order
     *
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */

    List<UserOrderResponseDto> viewAllCancelledOrders() throws NotFound;

    /**
     * This method is used to retrieve order by using orderId
     *
     * @param orderId it contains order id
     * @return UserOrderResponseDto it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    UserOrderResponseDto viewOrderById(Integer orderId) throws NotFound;

    /**
     * This method is used to placeOrder by using a cartId
     *
     * @param userOrderRequest it contains quantity, productId, addressId, userId
     * @param cartId id to placeOrder
     * @return SuccessResponseDto Order placed successfully
     * @throws NotFound order not confirmed
     */
    SuccessResponseDto placeOrder(UserOrderRequestDto userOrderRequest, Integer cartId) throws NotFound;

    /**
     * This method is used to update the deliveryStatus
     *
     * @param orderId
     * @return
     * @throws NotFound
     */
    SuccessResponseDto statusUpdate(Integer orderId) throws NotFound;

    /**
     * This method is used for placing order directly without cart
     *
     * @param userOrderRequest it contains quantity, productId, addressId, userId
     * @param userId it contains userId
     * @return SuccessResponseDto it shows success message
     * @throws NotFound please enter a valid user id
     */
    SuccessResponseDto buyNow(UserOrderRequestDto userOrderRequest, Integer userId) throws NotFound;

    /**
     * This method is used to retrieve all orders using productId
     *
     * @param productId it contains productId
     * @return List<OrderDetailsResponseDto> it contains categoryName,
     * subCategoryName, productName, quantity, price
     * @throws NotFound No record found
     */
    List<OrderDetailsResponseDto> viewOrdersByProductId(Integer productId) throws NotFound;

    /**
     * This method is used to retrieve order using userId
     *
     * @param userId it contains user id
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    List<UserOrderResponseDto> viewOrderByUserId(Integer userId) throws NotFound;

    /**
     * This method is used to Cancel the order
     *
     * @param orderId it contains order id
     * @return SuccessResponseDto order cancelled successfully
     * @throws NotFound Order not cancelled
     */
    SuccessResponseDto cancelOrderById(Integer orderId) throws NotFound, Existed;

    /**
     * This method is used for delivery person to get order by orderId
     *
     * @param orderId it contains orderId
     * @return OrderDeliveryResponseDto it contains userId, orderId,
     * totalPrice, totalQuantity, shippingAddress, orderStatus
     * @throws NotFound No record found
     */
    OrderDeliveryResponseDto getDeliveryOrder(Integer orderId) throws NotFound;

    /**
     * This method is used to retrieve orders by ordered date
     *
     * @param orderedDate it contains order date
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    List<UserOrderResponseDto> viewOrdersByDate(Date orderedDate) throws NotFound;

    /**
     * This method is used to retrieve particular orders
     * by using ordered date and user id
     *
     * @param orderedDate it contains order Date
     * @param userId it contains user id
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    List<UserOrderResponseDto> viewOrdersByIdAndDate(Date orderedDate, Integer userId) throws NotFound;

}
