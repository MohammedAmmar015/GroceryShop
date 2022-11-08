package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;

import java.util.List;

public interface UserOrderService {

    /**
     * <p>
     *     This method is used to retrieve all active orders
     * </p>
     * @return List<UserOrderResponseDto>
     */
    List<UserOrderResponseDto> viewAllActiveOrders() throws NotFoundException;

    /**
     * <p>
     *     This method is used to retrieve all the cancelled order
     * </p>
     * @return List<UserOrderResponseDto>
     */

    List<UserOrderResponseDto> viewAllCancelledOrders() throws NotFoundException;

    /**
     * <p>
     *     This method is used to retrieve order by using orderId
     * </p>
     * @param orderId
     * @return UserOrderResponseDto
     */
    UserOrderResponseDto viewOrderById(Integer orderId) throws NotFoundException;

    /**
     * <p>
     *     This method is used to placeOrder by using a cartId
     * </p>
     * @param userOrderRequestDto
     * @param cartId
     */
    SuccessDto placeOrder(UserOrderRequestDto userOrderRequestDto, Integer cartId) throws NotFoundException;

    /**
     * This method is used for placing order directly without cart
     * @param userOrderRequestDto
     * @param userId
     */
    SuccessDto buyNow(UserOrderRequestDto userOrderRequestDto, Integer userId) throws NotFoundException;

    /**
     * <p>
     *     This method is used to retrieve all orders using productId
     * </p>
     * @param productId
     * @return List<UserOrderResponseDto>
     */
    List<UserOrderResponseDto> viewOrdersByProductId(Integer productId) throws NotFoundException;

    /**
     * <p>
     *     This method is used to retrieve order using userId
     * </p>
     * @param user_id
     * @return List<UserOrderResponseDto>
     */
    List<UserOrderResponseDto> viewOrderByUserId(Integer user_id) throws NotFoundException;

    /**
     * <p>
     *     This method is used to Cancel the order
     * </p>
     * @param order_id
     * @return String
     */
    SuccessDto cancelOrderById(Integer order_id) throws NotFoundException;
}
