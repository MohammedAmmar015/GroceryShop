/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.OrderDetailsResponseDto;
import com.ideas2it.groceryshop.dto.OrderRequestDto;
import com.ideas2it.groceryshop.dto.OrderResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.OrderDetailsMapper;
import com.ideas2it.groceryshop.mapper.OrderMapper;
import com.ideas2it.groceryshop.model.*;
import com.ideas2it.groceryshop.repository.OrderRepository;
import com.ideas2it.groceryshop.service.OrderService;
import com.ideas2it.groceryshop.service.ProductService;
import com.ideas2it.groceryshop.service.StockService;
import com.ideas2it.groceryshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * <p>
 *     OrderServiceImpl contains all order related operations
 *     which helps the controller to communicate with repository .
 * </p>
 *
 * @author Dhanalakshmi
 * @version 1.0v
 * @since 2022-11-18
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AddressServiceImpl addressService;
    private final CartServiceImpl cartService;
    private final ProductService productService;
    private final UserService userService;
    private final StockService stockService;

    private final Logger logger = LogManager.getLogger(OrderService.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto placeOrder(OrderRequestDto orderRequest)
            throws NotFoundException {
        logger.debug("Entered placeOrder method in OrderServiceImpl");
        Cart cart = cartService.getActiveCartOfCurrentUser();
        if(cart != null) {
            Order order = new Order();
            order.setCart(cart);
            order.setUser(cart.getUser());
            order.setTotalQuantity(cart.getTotalQuantity());
            order.setTotalPrice(cart.getTotalPrice());
            List<CartDetails> cartDetails = cart.getCartDetails();
            List<OrderDetails> orderDetails= cartDetailsToOrderDetails(cartDetails);
            order.setOrderDetails(orderDetails);
            OrderDelivery orderDelivery = orderDelivery(orderRequest);
            order.setOrderDelivery(orderDelivery);
            orderRepository.save(order);
            cartService.removeCart();
            stockService.removeStockByOrderDetails(order, orderDelivery.getShippingAddress().getPinCode());
            logger.debug("Order placed successfully");
          return new SuccessResponseDto(200, "Order placed successfully");
        } else {
            logger.debug("Cart not found");
            throw new NotFoundException("Cart not found");
        }
    }

    /**
     * <p>
     *     This method is used for converting List<CartDetails> to List<OrderDetails>
     * </p>
     *
     * @param cartDetails - it contains quantity, price, productId
     * @return List<OrderDetails> - it shows list of order details which contains quantity, price, product
     */
    private List<OrderDetails> cartDetailsToOrderDetails(List<CartDetails> cartDetails) {
        logger.debug("Entered cartDetailsToOrderDetails method in OrderServiceImpl" );
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
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto buyNow(OrderRequestDto orderRequest) throws NotFoundException {
        logger.debug("Entered buyNow method in OrderServiceImpl");
        User user = userService.getCurrentUser();
        if (user != null) {
            Order order = new Order();
            List<OrderDetails> orderDetails = setOrderDetails(orderRequest);
            order.setOrderDetails(orderDetails);
            order.setTotalPrice(orderDetails.get(0).getPrice());
            order.setTotalQuantity(orderDetails.get(0).getQuantity());
            order.setUser(user);
            OrderDelivery orderDelivery = orderDelivery(orderRequest);
            order.setOrderDelivery(orderDelivery);
            orderRepository.save(order);
            stockService.removeStockByOrderDetails(order,
                    orderDelivery.getShippingAddress().getPinCode());
            logger.debug("Order placed successfully");
            return new SuccessResponseDto(200, "Order placed successfully");
        } else {
            logger.debug("User not found");
            throw new NotFoundException("User not found");
        }
    }

    /**
     * <p>
     *     This method is used to add orderDelivery details
     * </p>
     *
     * @param orderRequest it contains quantity, productId, addressId, userId
     * @return OrderDelivery it return expectedDeliveryDate, shippingAddress of an order
     * @throws NotFoundException if address is not found then it shows Address not found
     */
    private OrderDelivery orderDelivery(OrderRequestDto orderRequest)
            throws NotFoundException {
        logger.debug("Entered orderDelivery method in OrderServiceImpl");
        OrderDelivery orderDelivery = new OrderDelivery();
        Optional<Address> address = addressService.getAddressByAddressId(orderRequest.getAddressId());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 3);
        if(address.isPresent()) {
            orderDelivery.setExpectedDeliveryDate(calendar.getTime());
            orderDelivery.setShippingAddress(address.get());
            return orderDelivery;
        } else {
            logger.debug("Address not found");
            throw new NotFoundException("Address not found");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderResponseDto viewOrderById(Integer orderId) throws NotFoundException {
        logger.debug("Entered viewOrderById method in OrderServiceImpl");
        Integer userId = userService.getCurrentUser().getId();
        Optional<Order> order = orderRepository.findByIdAndUserId(orderId, userId);
        if (order.isPresent()) {
            return OrderMapper.entityToDto(order.get());
        } else {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
    }

    /**
     * <p>
     *     This method is used for calculating the total price
     *     and to set the orderDetails
     * </p>
     *
     * @param orderRequest it contains quantity, productId, addressId, userId of an order
     * @return List<OrderDetails> it returns list of order details which contains quantity, price, product
     */
    private List<OrderDetails> setOrderDetails(OrderRequestDto orderRequest) {
        logger.debug("Entered setOrderDetails method in OrderServiceImpl");
        OrderDetails orderDetail = new OrderDetails();
        List<OrderDetails> orderDetails = new ArrayList<>();
        Integer quantity = orderRequest.getQuantity();
        Integer productId = orderRequest.getProductId();
        Product product = productService.getProductById(productId);
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(quantity * product.getPrice());
        orderDetail.setProduct(product);
        orderDetails.add(orderDetail);
        return orderDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderResponseDto> viewAllActiveOrders() throws NotFoundException {
        logger.debug("Entered viewAllActiveOrders method in OrderServiceImpl");
        List<Order> orders = orderRepository.findByIsActive(true);
        if(orders != null) {
            return OrderMapper.getAllOrdersDto(orders);
        } else {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderResponseDto> viewAllCancelledOrders() throws NotFoundException {
        logger.debug("Entered viewAllCancelledOrders method in OrderServiceImpl");
        List<Order> orders = orderRepository.findByIsActive(false);
        if(!orders.isEmpty()) {
            return OrderMapper.getAllOrdersDto(orders);
        } else {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderResponseDto> viewOrderByUserId(Integer userId) throws NotFoundException {
        logger.debug("Entered viewOrderByUserId method in OrderServiceImpl");
        List<Order> orders = orderRepository.findByUserId(userId);
        if(!orders.isEmpty()) {
            return OrderMapper.getAllOrdersDto(orders);
        } else {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto cancelOrderById(Integer orderId) throws ExistedException {
        logger.debug("Entered cancelOrderById method in OrderServiceImpl");
        Integer userId = userService.getCurrentUser().getId();
        Optional<Order> order = orderRepository.findByIdAndIsActiveAndUserIdAndOrderDeliveryIsDelivered
                (orderId, true , userId, false);
        if(order.isPresent()) {
            Integer isCancelled = orderRepository.cancelOrderById(orderId, userId);
            stockService.updateStockByOrderDetails(order.get());
            if (isCancelled != 0) {
                logger.debug("Order cancelled successfully");
            }
            return new SuccessResponseDto(200, "Order cancelled successfully");
        } else {
            logger.debug("Order not found");
            throw new ExistedException("Order not found");
        }

    }

    /**
     * {@inheritDoc}
     */
   @Override
    public List<OrderDetailsResponseDto> viewOrdersByProductId(Integer productId) throws NotFoundException{
       logger.debug("Entered viewOrdersByProductId method in OrderServiceImpl");
        List<OrderDetails> orders = orderRepository.findByProductId(productId);
        if(!orders.isEmpty()) {
            return OrderDetailsMapper.getAllOrdersEntityToDto(orders);
        } else {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderResponseDto> viewOrdersByDate(Date orderedDate) throws NotFoundException {
        logger.debug("Entered viewOrdersByDate method in OrderServiceImpl");
        List<Order> orders =  orderRepository.findByOrderedDate(orderedDate);
        if(!orders.isEmpty()) {
            return OrderMapper.getAllOrdersDto(orders);
        } else {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderResponseDto> viewOrdersByIdAndDate(Date orderedDate, Integer userId)
            throws NotFoundException {
        logger.debug("Entered viewOrdersByIdAndDate method in OrderServiceImpl");
        List<Order> orders =  orderRepository.findByOrderedDateAndUserId(orderedDate, userId);
        if(!orders.isEmpty()) {
            return OrderMapper.getAllOrdersDto(orders);
        } else {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
    }

}
