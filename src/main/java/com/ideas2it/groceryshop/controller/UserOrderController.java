package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.*;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.helper.UserHelper;
import com.ideas2it.groceryshop.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *     USerOrder Controller is used to place and cancel order
 *     And we can also view the order by filter options
 * </p>
 *
 * @author Dhanalakshmi
 * @version 1.0V
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class UserOrderController {
    private final UserOrderService userOrderService;
    private final UserHelper userHelper;
    private final Logger logger = LogManager.getLogger(UserOrderController.class);

    /**
     * <p>
     *     This method is used to place order by using cart
     * </p>
     *
     * @param userOrderRequest - it contains addressId which is used to place order for the given address
     * @return SuccessResponseDto - Order placed successfully
     * @throws NotFound - if cart is not found it shows Cart not found
     */
    @PostMapping("/placeOrder/")
    public SuccessResponseDto placeOrder(@RequestBody UserOrderRequestDto userOrderRequest) throws NotFound {
        logger.debug("Entered placeOrder method in UserOrderController");
        return userOrderService.placeOrder(userOrderRequest);
    }

    /**
     * <p>
     *     This method is used to place order directly without adding it to cart by using current user
     * </p>
     * @param userOrderRequest - it contains quantity, productId, addressId
     *                          to place order directly without adding it to cart
     * @return SuccessResponseDto - Order placed successfully
     * @throws NotFound - if particular user is not found it shows User not found
     */
    @PostMapping("/buyNow/")
    public SuccessResponseDto buyNow(@RequestBody UserOrderRequestDto userOrderRequest
                             ) throws NotFound {
        Integer userId = userHelper.getCurrentUser().getId();
        logger.debug("Entered buyNow method in UserOrderController");
        return userOrderService.buyNow(userOrderRequest);
    }

    /**
     * <p>
     *     Deliver person changes the delivery status once the product is delivered
     * </p>
     *
     * @param orderId - it contains order id for changing the delivery status
     * @return SuccessResponseDto - Order delivered successfully
     * @throws NotFound - if particular order is not found it shows No record found
     */
    @PutMapping("/statusUpdate/{orderId}")
    public SuccessResponseDto statusUpdate(@PathVariable Integer orderId) throws NotFound {
        logger.debug("Entered statusUpdate method in UserOrderController");
        return userOrderService.statusUpdate(orderId);
    }

    /**
     * <p>
     *     This method is useful for Admin to retrieve all active orders
     * </p>
     * @return List<UserOrderResponseDto> - it returns list of active orders which contains userId,
     *                                     orderedDate, expectedDeliveryDate, totalPrice,
     *                                     totalQuantity, orderDetails, isDelivered for each order
     * @throws NotFound - if order is not placed it shows No record found exception
     */
    @GetMapping("/activeOrders")
    public List<UserOrderResponseDto> viewAllActiveOrders() throws NotFound {
        logger.debug("Entered viewAllActiveOrders method in UserOrderController");
       return userOrderService.viewAllActiveOrders();
    }

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
    @GetMapping("/cancelledOrders")
    public List<UserOrderResponseDto> viewAllCancelledOrders() throws NotFound {
        logger.debug("Entered viewAllCancelledOrders method in UserOrderController");
        return userOrderService.viewAllCancelledOrders();
    }

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
    @GetMapping("/{orderId}")
    public UserOrderResponseDto viewOrderById(@PathVariable Integer orderId) throws NotFound {
        logger.debug("Entered viewOrderById method in UserOrderController");
        return userOrderService.viewOrderById(orderId);
    }

    /**
     * <p>
     *     This method is used to retrieve All orders of particular user
     * </p>
     *
     * @return List<UserOrderResponseDto> - it returns a particular user order which contains userId,
     *                                      orderedDate, expectedDeliveryDate, totalPrice,
     *                                      totalQuantity, orderDetails, isDelivered
     * @throws NotFound - if order of particular user is not available it shows No record found
     */
    @GetMapping("/user")
    public List<UserOrderResponseDto> viewOrderByUserId() throws NotFound {
        Integer userId = userHelper.getCurrentUser().getId();
        logger.debug("Entered viewOrderByUserId method in UserOrderController");
        return userOrderService.viewOrderByUserId(userId);
    }

    /**
     * <p>
     *     This method is useful for Customer to cancel order
     * </p>
     *
     * @param orderId - to cancel order by using order id
     * @return SuccessResponseDto - order cancelled successfully
     * @throws Existed - if order is already cancelled it shows Order already cancelled
     */
    @PutMapping("/cancelOrder/{orderId}")
    public SuccessResponseDto cancelOrder(@PathVariable() Integer orderId) throws Existed {
        logger.debug("Entered cancelOrder method in UserOrderController");
        return userOrderService.cancelOrderById(orderId);
    }

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
    @GetMapping("/products/{productId}")
    public List<OrderDetailsResponseDto> viewOrderByProductId(@PathVariable Integer productId)
            throws NotFound {
        logger.debug("Entered viewOrderByProductId method in UserOrderController");
        return userOrderService.viewOrdersByProductId(productId);
    }

    /**
     * <p>
     *     This method is used for delivery person to pick order
     * </p>
     * @param orderId - to pick order by orderId
     * @return OrderDeliveryResponseDto - it show particular order which contains userId, orderId,
     *                                    totalPrice, totalQuantity, shippingAddress, orderStatus
     * @throws NotFound - if order not found for particular order id it shows No record found
     */
    @GetMapping("/orderDelivery/{orderId}")
    public OrderDeliveryResponseDto getDeliveryOrder(@PathVariable Integer orderId) throws NotFound {
        logger.debug("Entered getDeliveryOrder method in UserOrderController");
        return userOrderService.getDeliveryOrder(orderId);
    }

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
    @GetMapping("/date/{orderedDate}")
    public List<UserOrderResponseDto> viewOrdersByDate(@PathVariable String orderedDate)
            throws NotFound, ParseException {
        logger.debug("Entered viewOrdersByDate method in UserOrderController");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(orderedDate);
        return userOrderService.viewOrdersByDate(date);
    }

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
    @GetMapping("/{orderedDate}/{userId}")
    public List<UserOrderResponseDto> viewOrdersByIdAndDate(@PathVariable String orderedDate,
                                                            @PathVariable Integer userId)
                                                            throws NotFound, ParseException {
        logger.debug("Entered viewOrdersByIdAndDate method in UserOrderController");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(orderedDate);
        return userOrderService.viewOrdersByIdAndDate(date, userId);
    }

}
