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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;
import java.util.Calendar;


/**
 * This class acts like a intermediate between UserOrderController and Repository.
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

    /**
     * This method is used to placeOrder by using a cartId
     *
     * @param userOrderRequest it contains quantity, productId, addressId, userId
     * @param cartId id to placeOrder
     * @return SuccessResponseDto Order placed successfully
     * @throws NotFound order not confirmed
     */
    @Override
    public SuccessResponseDto placeOrder(UserOrderRequestDto userOrderRequest, Integer cartId)
            throws NotFound {
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
          return new SuccessResponseDto(202, "Order placed successfully");
        } else {
            throw new NotFound("Order not confirmed");
        }
    }

    /**
     * This method is used for converting List<CartDetails> to List<OrderDetails>
     *
     * @param cartDetails it contains cartId, quantity, price, productId, isActive
     * @return List<OrderDetails> it contains quantity, price, product
     */
    private List<OrderDetails> cartDetailsToOrderDetails(List<CartDetails> cartDetails) {
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
     * This method is used for placing order directly without cart
     *
     * @param userOrderRequest it contains quantity, productId, addressId, userId
     * @param userId it contains userId
     * @return SuccessResponseDto it shows success message
     * @throws NotFound please enter a valid user id
     */
    @Override
    public SuccessResponseDto buyNow(UserOrderRequestDto userOrderRequest, Integer userId) throws NotFound {
        Optional<User> user = userHelper.findUserById(userId);
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
            return new SuccessResponseDto(202, "Order placed successfully");
        } else {
            throw new NotFound("Please enter a valid userId");
        }
    }

    /**
     * This method is used to add orderDelivery details
     *
     * @param userOrderRequest it contains quantity, productId, addressId, userId
     * @return OrderDelivery expectedDeliveryDate, shippingAddress
     * @throws Address not found
     */
    private OrderDelivery orderDelivery(UserOrderRequestDto userOrderRequest)
            throws NotFound {
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
            throw new NotFound("Address not found");
        }
    }

    /**
     * This method is used for calculating the total price
     * and to set the orderDetails
     *
     * @param userOrderRequest it contains quantity, productId, addressId, userId
     * @return List<OrderDetails> it contains quantity, price, product
     */
    private List<OrderDetails> setOrderDetails(UserOrderRequestDto userOrderRequest) {
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
     * This method is used to update the delivery status
     *
     * @param orderId it contains order id
     * @return SuccessResponseDto Order delivered successfully
     * @throws NotFound No record found
     */
    @Override
    public SuccessResponseDto statusUpdate(Integer orderId) throws NotFound {
        UserOrderResponseDto userOrderResponse = viewOrderById(orderId);
        if(userOrderResponse.getIsDelivered() != true) {
            Integer statusUpdation = orderDeliveryRepo.updateStatus(orderId);
            return new SuccessResponseDto(202, "Order delivered successfully");
        } else {
            throw new NotFound("No record found");
        }
    }

    /**
     * This method is used to retrieve all active orders
     *
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    @Override
    public List<UserOrderResponseDto> viewAllActiveOrders() throws NotFound {
        List<UserOrder> orders = userOrderRepo.findByIsActive(true);
        if(orders != null) {
            List<UserOrderResponseDto> activeOrders = UserOrderMapper.getAllOrdersDto(orders);
            return activeOrders;
        } else {
            throw new NotFound("No record found");
        }
    }

    /**
     * This method is used to retrieve all the cancelled order
     *
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    @Override
    public List<UserOrderResponseDto> viewAllCancelledOrders() throws NotFound {
        List<UserOrder> orders = userOrderRepo.findByIsActive(false);
        if(!orders.isEmpty()) {
            List<UserOrderResponseDto> cancelledOrders = UserOrderMapper.getAllOrdersDto(orders);
            return cancelledOrders;
        } else {
            throw new NotFound("No record found");
        }
    }

    /**
     * This method is used to retrieve order by using orderId
     *
     * @param orderId it contains order id
     * @return UserOrderResponseDto it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    @Override
    public UserOrderResponseDto viewOrderById(Integer orderId) throws NotFound {
        Optional<UserOrder> userOrder = userOrderRepo.findById(orderId);
        if (!userOrder.isEmpty()) {
            return UserOrderMapper.entityToDto(userOrder.get());
        } else {
            throw new NotFound("No record found");
        }
    }

    /**
     * This method is used to retrieve order using userId
     *
     * @param userId it contains user id
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    @Override
    public List<UserOrderResponseDto> viewOrderByUserId(Integer userId) throws NotFound {
        List<UserOrder> userOrder = userOrderRepo.findByUserId(userId);
        if(!userOrder.isEmpty()) {
            List<UserOrderResponseDto> orders = UserOrderMapper.getAllOrdersDto(userOrder);
            return orders;
        } else {
            throw new NotFound("No record found");
        }
    }

    /**
     * This method is used to Cancel the order
     *
     * @param orderId it contains order id
     * @return SuccessResponseDto order cancelled successfully
     * @throws NotFound Order not cancelled
     */
    @Override
    public SuccessResponseDto cancelOrderById(Integer orderId) throws NotFound, Existed {
        Boolean isActive = viewOrderById(orderId).getIsDelivered();
        if(!isActive) {
            Integer isCancelled = userOrderRepo.cancelOrderbyId(orderId);
            if (isCancelled != 0) {
                return new SuccessResponseDto(202, "Order cancelled successfully");
            } else {
                throw new NotFound("Order not cancelled");
            }
        } else {
            throw new Existed("Order already cancelled");
        }

    }

    /**
     * This method is used to retrieve all orders using productId
     *
     * @param productId it contains productId
     * @return List<OrderDetailsResponseDto> it contains categoryName,
     * subCategoryName, productName, quantity, price
     * @throws NotFound No record found
     */
   @Override
    public List<OrderDetailsResponseDto> viewOrdersByProductId(Integer productId) throws NotFound{
        List<OrderDetails> userOrders = userOrderRepo.findByProductId(productId);
        if(!userOrders.isEmpty()) {
            List<OrderDetailsResponseDto> orders = OrderDetailsMapper.getAllOrdersEntityToDto(userOrders);
            return orders;
        } else {
            throw new NotFound("No record found");
        }
    }

    /**
     * This method is used for delivery person to get order by orderId
     *
     * @param orderId it contains orderId
     * @return OrderDeliveryResponseDto it contains userId, orderId,
     * totalPrice, totalQuantity, shippingAddress, orderStatus
     * @throws NotFound No record found
     */
    @Override
    public OrderDeliveryResponseDto getDeliveryOrder(Integer orderId) throws NotFound {
        OrderDelivery orderDelivery = orderDeliveryRepo.findByUserOrderId(orderId);
        if(orderDelivery != null) {
            return OrderDeliveryMapper.entityToDto(orderDelivery);
        } else {
            throw new NotFound("No record found");
        }
    }

    /**
     * This method is used retrieve all orders by date
     *
     * @param orderedDate it contains order date
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    @Override
    public List<UserOrderResponseDto> viewOrdersByDate(Date orderedDate) throws NotFound {
        List<UserOrder> userOrders =  userOrderRepo.findByOrderedDate(orderedDate);
        if(!userOrders.isEmpty()) {
            return UserOrderMapper.getAllOrdersDto(userOrders);
        } else {
            throw new NotFound("No record found");
        }

    }

    /**
     * This method is used to retrieve orders by ordered date and userId
     *
     * @param orderedDate it contains order Date
     * @param userId it contains user id
     * @return List<UserOrderResponseDto> it contains userId,
     * orderedDate, expectedDeliveryDate, totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFound No record found
     */
    @Override
    public List<UserOrderResponseDto> viewOrdersByIdAndDate(Date orderedDate, Integer userId)
            throws NotFound {
        List<UserOrder> userOrders =  userOrderRepo.findByOrderedDateAndUserId(orderedDate, userId);
        if(!userOrders.isEmpty()) {
            return UserOrderMapper.getAllOrdersDto(userOrders);
        } else {
            throw new NotFound("No record found");
        }
    }

}
