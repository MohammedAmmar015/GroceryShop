package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;

import java.util.List;

public interface UserOrderService {

    /**
     * <p>
     *     This method is used to retrieve all active orders
     * </p>
     * @return List<UserOrderResponseDto>
     */
    List<UserOrderResponseDto> viewAllActiveOrders();

    /**
     * <p>
     *     This method is used to retrieve all the cancelled order
     * </p>
     * @return List<UserOrderResponseDto>
     */

    List<UserOrderResponseDto> viewAllCancelledOrders();

    /**
     * <p>
     *     This method is used to retrieve order by using orderId
     * </p>
     * @param orderId
     * @return UserOrderResponseDto
     */
    UserOrderResponseDto viewOrderById(Integer orderId);

    /**
     * <p>
     *     This method is used to placeOrder by using a cartId
     * </p>
     * @param userOrderRequestDto
     * @param cartId
     */
    void placeOrder(UserOrderRequestDto userOrderRequestDto, Integer cartId);

    /**
     * <p>
     *     This method is used for placing order directly without cart
     * </p>
     * @param userOrderRequestDto
     * @param userId
     */
    void buyNow(UserOrderRequestDto userOrderRequestDto, Integer userId);

    /**
     * <p>
     *     This method is used to retrieve all orders using productId
     * </p>
     * @param productId
     * @return List<UserOrderResponseDto>
     */
    List<UserOrderResponseDto> viewOrdersByProductId(Integer productId);

    /**
     * <p>
     *     This method is used to retrieve order using userId
     * </p>
     * @param user_id
     * @return List<UserOrderResponseDto>
     */
    List<UserOrderResponseDto> viewOrderByUserId(Integer user_id);

    /**
     * <p>
     *     This method is used to Cancel the order
     * </p>
     * @param order_id
     * @return String
     */
    String cancelOrderById(Integer order_id);
}
