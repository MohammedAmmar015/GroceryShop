/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.*;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;

import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;

import com.ideas2it.groceryshop.helper.CartHelper;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.helper.StockHelper;
import com.ideas2it.groceryshop.helper.UserHelper;

import com.ideas2it.groceryshop.mapper.OrderDeliveryMapper;
import com.ideas2it.groceryshop.mapper.OrderDetailsMapper;
import com.ideas2it.groceryshop.mapper.UserOrderMapper;

import com.ideas2it.groceryshop.model.UserOrder;
import com.ideas2it.groceryshop.model.OrderDelivery;
import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.CartDetails;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.User;
import com.ideas2it.groceryshop.model.Address;

import com.ideas2it.groceryshop.repository.AddressRepo;
import com.ideas2it.groceryshop.repository.OrderDeliveryRepo;
import com.ideas2it.groceryshop.repository.UserOrderRepo;

import com.ideas2it.groceryshop.service.UserOrderService;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;
import java.util.Calendar;


/**
 * <p>
 *     This class is used for connecting UserOrderController and Repository which implements UserOrderService.
 * </p>
 *
 * @author Dhanalakshmi
 * @version 1.0v
 */
@Service
@RequiredArgsConstructor
@ControllerAdvice
public class UserOrderServiceImpl implements UserOrderService {
    private final UserOrderRepo userOrderRepo;
    private final OrderDeliveryRepo orderDeliveryRepo;
    private final AddressRepo addressRepo;
    private final CartHelper cartHelper;
    private final ProductHelper productHelper;
    private final UserHelper userHelper;
    private final StockHelper stockHelper;

    private final Logger logger;

    /**
     * <p>
     *     This method is used to place order by cartId
     * </p>
     *
     * @param userOrderRequest it contains quantity, productId, addressId,
     *                         userId, and with cartId place order
     * @return SuccessResponseDto Order placed successfully
     * @throws NotFound if cart is not found it shows Cart not found
     */
    @Override
    public SuccessResponseDto placeOrder(UserOrderRequestDto userOrderRequest, Integer cartId)
            throws NotFound {
        logger.debug("Entered placeOrder method in UserOrderServiceImpl");
        Cart cart = cartHelper.getCartById(cartId, true);
        if(cart != null) {
            UserOrder userOrder = new UserOrder();
            userOrder.setCart(cart);
            userOrder.setUser(cart.getUser());
            userOrder.setTotalQuantity(cart.getTotalQuantity());
            userOrder.setTotalPrice(cart.getTotalPrice());
            List<CartDetails> cartDetails = cart.getCartDetails();
            List<OrderDetails> orderDetails= cartDetailsToOrderDetails(cartDetails);
            userOrder.setOrderDetails(orderDetails);
            OrderDelivery orderDelivery = orderDelivery(userOrderRequest);
            userOrder.setOrderDelivery(orderDelivery);
            userOrderRepo.save(userOrder);
            cartHelper.deleteAllProductsFromCart(cart.getUser());
            stockHelper.removeStockByOrderDetails(userOrder,
                    orderDelivery.getShippingAddress().getPinCode());
            logger.debug("Order placed successfully");
          return new SuccessResponseDto(202, "Order placed successfully");
        } else {
            logger.debug("Cart not found");
            throw new NotFound("Cart not found");
        }
    }

    /**
     * <p>
     *     This method is used for converting List<CartDetails> to List<OrderDetails>
     * </p>
     *
     * @param cartDetails it contains cartId, quantity, price, productId, isActive
     * @return List<OrderDetails> it shows list of order details which contains quantity, price, product
     */
    private List<OrderDetails> cartDetailsToOrderDetails(List<CartDetails> cartDetails) {
        logger.debug("Entered cartDetailsToOrderDetails method in UserOrderServiceImpl" );
        List<OrderDetails> orderDetails = new ArrayList<>();
        for(CartDetails cartDetail : cartDetails) {
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setPrice(cartDetail.getPrice());
            orderDetail.setProduct(cartDetail.getProduct());
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    /**
     * <p>
     *     This method is used to place order directly without adding it to cart
     * </p>
     * @param userOrderRequest, it contains quantity, productId, addressId,
     *                          and userId to place order directly
     * @return SuccessResponseDto Order placed successfully
     * @throws NotFound if particular user is not found it shows User not found
     */
    @Override
    public SuccessResponseDto buyNow(UserOrderRequestDto userOrderRequest, Integer userId) throws NotFound {
        Optional<User> user = userHelper.findUserById(userId);
        logger.debug("Entered buyNow method in UserOrderServiceImpl");
        if (user.isPresent()) {
            UserOrder userOrder = new UserOrder();
            List<OrderDetails> orderDetails = setOrderDetails(userOrderRequest);
            userOrder.setOrderDetails(orderDetails);
            userOrder.setTotalPrice(orderDetails.get(0).getPrice());
            userOrder.setTotalQuantity(orderDetails.get(1).getQuantity());
            userOrder.setUser(user.get());
            OrderDelivery orderDelivery = orderDelivery(userOrderRequest);
            userOrder.setOrderDelivery(orderDelivery);
            userOrderRepo.save(userOrder);
            logger.debug("Order placed successfully");
            return new SuccessResponseDto(202, "Order placed successfully");
        } else {
            logger.debug("User not found");
            throw new NotFound("User not found");
        }
    }

    /**
     * <p>
     *     This method is used to add orderDelivery details
     * </p>
     *
     * @param userOrderRequest it contains quantity, productId, addressId, userId
     * @return OrderDelivery it return expectedDeliveryDate, shippingAddress of an order
     * @throws NotFound if address is not found then it shows Address not found
     */
    private OrderDelivery orderDelivery(UserOrderRequestDto userOrderRequest)
            throws NotFound {
        logger.debug("Entered orderDelivery method in UserOrderServiceImpl");
        OrderDelivery orderDelivery = new OrderDelivery();
        Optional<Address> address = addressRepo.findByIsActiveAndId(true,
                                                userOrderRequest.getAddressId());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 3);
        if(address.isPresent()) {
            orderDelivery.setExpectedDeliveryDate(calendar.getTime());
            orderDelivery.setShippingAddress(address.get());
            return orderDelivery;
        } else {
            logger.debug("Address not found");
            throw new NotFound("Address not found");
        }
    }

    /**
     * <p>
     *     This method is used for calculating the total price
     *     and to set the orderDetails
     * </p>
     *
     * @param userOrderRequest it contains quantity, productId, addressId, userId of an order
     * @return List<OrderDetails> it returns list of order details which contains quantity, price, product
     */
    private List<OrderDetails> setOrderDetails(UserOrderRequestDto userOrderRequest) {
        logger.debug("Entered setOrderDetails method in UserOrderServiceImpl");
        OrderDetails orderDetail = new OrderDetails();
        List<OrderDetails> orderDetails = new ArrayList<>();
        Integer quantity = userOrderRequest.getQuantity();
        Integer productId = userOrderRequest.getProductId();
        Product product = productHelper.getProductById(productId);
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(quantity * product.getPrice());
        orderDetail.setProduct(product);
        orderDetails.add(orderDetail);
        return orderDetails;
    }

    /**
     * <p>
     *     Deliver person changes the delivery status once the product is delivered
     * </p>
     *
     * @param orderId it contains order id for changing the delivery status
     * @return SuccessResponseDto Order delivered successfully
     * @throws NotFound if particular order is not found it shows No record found
     */
    @Override
    public SuccessResponseDto statusUpdate(Integer orderId) throws NotFound {
        logger.debug("Entered statusUpdate method in UserOrderServiceImpl");
        UserOrderResponseDto userOrderResponse = viewOrderById(orderId);
        if(userOrderResponse.getIsDelivered() != true) {
            Integer statusUpdation = orderDeliveryRepo.updateStatus(orderId);
            logger.debug("Order delivered successfully");
            return new SuccessResponseDto(202, "Order delivered successfully");
        } else {
            logger.debug("No record found");
            throw new NotFound("No record found");
        }
    }

    /**
     * <p>
     *     This method is used to retrieve all orders
     * </p>
     * @return List<UserOrderResponseDto> it returns list of active orders which contains userId,
     *                                    orderedDate, expectedDeliveryDate, totalPrice,
     *                                    totalQuantity, orderDetails, isDelivered for each order
     * @throws NotFound if order is not placed it shows No record found exception
     */
    @Override
    public List<UserOrderResponseDto> viewAllActiveOrders() throws NotFound {
        logger.debug("Entered viewAllActiveOrders method in UserOrderServiceImpl");
        List<UserOrder> orders = userOrderRepo.findByIsActive(true);
        if(orders != null) {
            List<UserOrderResponseDto> activeOrders = UserOrderMapper.getAllOrdersDto(orders);
            return activeOrders;
        } else {
            logger.debug("No record found");
            throw new NotFound("No record found");
        }
    }

    /**
     * <p>
     *     This method is used to view all cancelled orders
     * </p>
     *
     * @return List<UserOrderResponseDto> it returns list of cancelled orders which contains userId,
     *                                    orderedDate, expectedDeliveryDate, totalPrice,
     *                                    totalQuantity, orderDetails, isDelivered
     * @throws NotFound if cancelled orders is not found it shows No record found
     */
    @Override
    public List<UserOrderResponseDto> viewAllCancelledOrders() throws NotFound {
        logger.debug("Entered viewAllCancelledOrders method in UserOrderServiceImpl");
        List<UserOrder> orders = userOrderRepo.findByIsActive(false);
        if(!orders.isEmpty()) {
            List<UserOrderResponseDto> cancelledOrders = UserOrderMapper.getAllOrdersDto(orders);
            return cancelledOrders;
        } else {
            logger.debug("No record found");
            throw new NotFound("No record found");
        }
    }

    /**
     * <p>
     *     This method is used to retrieve Order by orderId
     * </p>
     *
     * @param orderId with order id the order will be retrieved
     * @return UserOrderResponseDto it returns a particular order which contains userId,
     *                              orderedDate, expectedDeliveryDate, totalPrice,
     *                              totalQuantity, orderDetails, isDelivered
     * @throws NotFound if order is not found it shows No record found
     */
    @Override
    public UserOrderResponseDto viewOrderById(Integer orderId) throws NotFound {
        logger.debug("Entered viewOrderById method in UserOrderServiceImpl");
        Optional<UserOrder> userOrder = userOrderRepo.findById(orderId);
        if (!userOrder.isEmpty()) {
            return UserOrderMapper.entityToDto(userOrder.get());
        } else {
            logger.debug("No record found");
            throw new NotFound("No record found");
        }
    }

    /**
     * <p>
     *     This method is used to retrieve All orders of particular user
     * </p>
     *
     * @param userId with user id we can retrieve list of order of a particular user
     * @return List<UserOrderResponseDto> it returns a particular user order which contains userId,
     *                                    orderedDate, expectedDeliveryDate, totalPrice,
     *                                    totalQuantity, orderDetails, isDelivered
     * @throws NotFound if order of particular user is not available it shows No record found
     */
    @Override
    public List<UserOrderResponseDto> viewOrderByUserId(Integer userId) throws NotFound {
        logger.debug("Entered viewOrderByUserId method in UserOrderServiceImpl");
        List<UserOrder> userOrder = userOrderRepo.findByUserId(userId);
        if(!userOrder.isEmpty()) {
            List<UserOrderResponseDto> orders = UserOrderMapper.getAllOrdersDto(userOrder);
            return orders;
        } else {
            logger.debug("No record found");
            throw new NotFound("No record found");
        }
    }

    /**
     * <p>
     *     This method is used to cancel order
     * </p>
     *
     * @param orderId to cancel order by using order id
     * @return SuccessResponseDto order cancelled successfully
     * @throws NotFound if order is not found it shows Order not found
     */
    @Override
    public SuccessResponseDto cancelOrderById(Integer orderId) throws NotFound, Existed {
        logger.debug("Entered cancelOrderById method in UserOrderServiceImpl");
        Boolean isActive = viewOrderById(orderId).getIsDelivered();
        if(!isActive) {
            Integer isCancelled = userOrderRepo.cancelOrderbyId(orderId);
            if (isCancelled != 0) {
                logger.debug("Order cancelled successfully");
                return new SuccessResponseDto(202, "Order cancelled successfully");
            } else {
                logger.debug("Order not found");
                throw new NotFound("Order not found");
            }
        } else {
            logger.debug("Order already cancelled");
            throw new Existed("Order already cancelled");
        }

    }

    /**
     * <p>
     *     This method is used to retrieve list of orders of particular product by productId
     * </p>
     *
     * @param productId with product id we can retrieve the order placed for particular product
     * @return List<OrderDetailsResponseDto> it contains list of orders of particular product
     *                                      which contains categoryName, subCategoryName,
     *                                      productName, quantity, price
     * @throws NotFound if order of particular product not found it shows No record found
     */
   @Override
    public List<OrderDetailsResponseDto> viewOrdersByProductId(Integer productId) throws NotFound{
       logger.debug("Entered viewOrdersByProductId method in UserOrderServiceImpl");
        List<OrderDetails> userOrders = userOrderRepo.findByProductId(productId);
        if(!userOrders.isEmpty()) {
            List<OrderDetailsResponseDto> orders = OrderDetailsMapper.getAllOrdersEntityToDto(userOrders);
            return orders;
        } else {
            logger.debug("No record found");
            throw new NotFound("No record found");
        }
    }

    /**
     * <p>
     *     This method is used for delivery person to pick order
     * </p>
     * @param orderId to pick order by orderId
     * @return OrderDeliveryResponseDto it show particular order which contains userId, orderId,
     *                                  totalPrice, totalQuantity, shippingAddress, orderStatus
     * @throws NotFound if order not found for particular order id it shows No record found
     */
    @Override
    public OrderDeliveryResponseDto getDeliveryOrder(Integer orderId) throws NotFound {
        logger.debug("Entered getDeliveryOrder method in UserOrderServiceImpl");
        OrderDelivery orderDelivery = orderDeliveryRepo.findByUserOrderId(orderId);
        if(orderDelivery != null) {
            return OrderDeliveryMapper.entityToDto(orderDelivery);
        } else {
            logger.debug("No record found");
            throw new NotFound("No record found");
        }
    }

    /**
     * <p>
     *     This method is used retrieve all orders by date
     * </p>
     * @param orderedDate to view orders of particular date by using order date
     * @return List<UserOrderResponseDto> it shows list of orders which contains userId,
     *                                    orderedDate, expectedDeliveryDate, totalPrice,
     *                                    totalQuantity, orderDetails, isDelivered
     * @throws NotFound if order is not placed for mentioned date then it shows No record found
     */
    @Override
    public List<UserOrderResponseDto> viewOrdersByDate(Date orderedDate) throws NotFound {
        logger.debug("Entered viewOrdersByDate method in UserOrderServiceImpl");
        List<UserOrder> userOrders =  userOrderRepo.findByOrderedDate(orderedDate);
        if(!userOrders.isEmpty()) {
            return UserOrderMapper.getAllOrdersDto(userOrders);
        } else {
            logger.debug("No record found");
            throw new NotFound("No record found");
        }

    }

    /**
     * <p>
     *     This method is used to retrieve order of a particular user as per mentioned date
     * </p>
     *
     * @param orderedDate
     * @param userId to get order of particular date of a user by using order date and user id
     * @return List<UserOrderResponseDto> it shows list of orders which contains userId,
     *                                    orderedDate, expectedDeliveryDate, totalPrice,
     *                                    totalQuantity, orderDetails, isDelivered
     * @throws NotFound if order is not placed on the date by particular user then it shows No record found
     */
    @Override
    public List<UserOrderResponseDto> viewOrdersByIdAndDate(Date orderedDate, Integer userId)
            throws NotFound {
        logger.debug("Entered viewOrdersByIdAndDate method in UserOrderServiceImpl");
        List<UserOrder> userOrders =  userOrderRepo.findByOrderedDateAndUserId(orderedDate, userId);
        if(!userOrders.isEmpty()) {
            return UserOrderMapper.getAllOrdersDto(userOrders);
        } else {
            logger.debug("No record found");
            throw new NotFound("No record found");
        }
    }

}
