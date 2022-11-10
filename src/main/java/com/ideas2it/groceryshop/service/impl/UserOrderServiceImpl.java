package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.*;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.helper.CartHelper;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.helper.StockHelper;
import com.ideas2it.groceryshop.helper.UserHelper;
import com.ideas2it.groceryshop.mapper.OrderDeliveryMapper;
import com.ideas2it.groceryshop.mapper.OrderDetailsMapper;
import com.ideas2it.groceryshop.mapper.UserOrderMapper;
import com.ideas2it.groceryshop.model.*;
import com.ideas2it.groceryshop.repository.AddressRepo;
import com.ideas2it.groceryshop.repository.OrderDeliveryRepo;
import com.ideas2it.groceryshop.repository.UserOrderRepo;
import com.ideas2it.groceryshop.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
     * @param userOrderRequestDto
     * @param cartId
     * @throws NotFound
     */
    @Override
    public SuccessDto placeOrder(UserOrderRequestDto userOrderRequestDto, Integer cartId) throws NotFound {
        Cart cart = cartHelper.getCartById(cartId, true);
        if(cart != null) {
            UserOrder userOrder = new UserOrder();
            userOrder.setCart(cart);
            userOrder.setUser(cart.getUser());
            userOrder.setTotalPrice(cart.getTotalPrice());
            List<CartDetails> cartDetails = cart.getCartDetails();
            List<OrderDetails> orderDetails= cartDetailsToOrderDetails(cartDetails);
            userOrder.setOrderDetails(orderDetails);
            userOrderRepo.save(userOrder);
            cartHelper.deleteAllProductsFromCart(cart.getUser());
            return orderDelivery(userOrderRequestDto, userOrder);
        } else {
            throw new NotFound("Order is Not confirmed!!!");
        }
    }

    /**
     * This method is used for converting List<CartDetails> to List<OrderDetails>
     *
     * @param cartDetails
     * @return List<OrderDetails>
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
     * @param userOrderRequestDto
     * @param userId
     * @throws NotFound
     */
    @Override

    public SuccessDto buyNow(UserOrderRequestDto userOrderRequestDto, Integer userId) throws NotFound {
        Optional<User> user = userHelper.findUserById(userId);
        if (user.isPresent()) {
            UserOrder userOrder = new UserOrder();
            List<OrderDetails> orderDetails = setOrderDetails(userOrderRequestDto);
            userOrder.setOrderDetails(orderDetails);
            userOrder.setTotalPrice(orderDetails.get(0).getPrice());
            userOrder.setUser(user.get());
            userOrderRepo.save(userOrder);
            return orderDelivery(userOrderRequestDto, userOrder);
        } else {
            throw new NotFound("Please Enter a valid UserId");
        }
    }

    /**
     * This method is used to add orderDelivery details
     *
     * @param userOrderRequestDto
     * @param userOrder
     */
    private SuccessDto orderDelivery(UserOrderRequestDto userOrderRequestDto, UserOrder userOrder) throws NotFound {
        OrderDelivery orderDelivery = new OrderDelivery();
        Optional<Address> address = addressRepo.findByIsActiveAndId(true, userOrderRequestDto.getAddressId());
        if(address.isPresent()) {
            orderDelivery.setUserOrder(userOrder);
            orderDelivery.setShippingAddress(address.get());
            orderDeliveryRepo.save(orderDelivery);
            stockHelper.removeStockByOrderDetails(userOrder, address.get().getPinCode());
        } else {
            throw new NotFound("Address Not Found!!!");
        }
        return new SuccessDto(202, "Order Placed Successfully");
    }

    /**
     * <p>
     *     This method is used for calculating the total price
     *     and to set the orderDetails
     * </p>
     * @param userOrderRequestDto
     * @return List<OrderDetails>
     */
    private List<OrderDetails> setOrderDetails(UserOrderRequestDto userOrderRequestDto) {
        OrderDetails orderDetail = new OrderDetails();
        List<OrderDetails> orderDetails = new ArrayList<>();
        Integer quantity = userOrderRequestDto.getQuantity();
        Integer productId = userOrderRequestDto.getProductId();
        Product product = productHelper.getProductById(productId);
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(quantity * product.getPrice());
        orderDetail.setProduct(product);
        orderDetails.add(orderDetail);
        return orderDetails;
    }

    /**
     * This method is used to retrieve all active orders
     *
     * @return List<UserOrderResponseDto>
     * @throws NotFound
     */
    @Override
    public List<UserOrderResponseDto> viewAllActiveOrders() throws NotFound {
        List<UserOrder> orders = userOrderRepo.findByIsActive(true);
        if(orders != null) {
            List<UserOrderResponseDto> viewPlacedOrders = UserOrderMapper.getAllOrdersDto(orders);
            return viewPlacedOrders;
        } else {
            throw new NotFound("No Record Found!!!");
        }
    }

    /**
     * This method is used to retrieve all the cancelled order
     *
     * @return List<UserOrderResponseDto>
     * @throws NotFound
     */
    @Override
    public List<UserOrderResponseDto> viewAllCancelledOrders() throws NotFound {
        List<UserOrder> orders = userOrderRepo.findByIsActive(false);
        if(!orders.isEmpty()) {
            List<UserOrderResponseDto> viewCancelledOrders = UserOrderMapper.getAllOrdersDto(orders);
            return viewCancelledOrders;
        } else {
            throw new NotFound("No Record Found!!!");
        }
    }

    /**
     * This method is used to retrieve order by using orderId
     * @param orderId
     * @return UserOrderResponseDto
     * @throws NotFound
     */
    @Override
    public UserOrderResponseDto viewOrderById(Integer orderId) throws NotFound {
        Optional<UserOrder> userOrder = userOrderRepo.findById(orderId);
        if (!userOrder.isEmpty()) {
            return UserOrderMapper.entityToDto(userOrder.get());
        } else {
            throw new NotFound("No Record Found!!!");
        }
    }

    /**
     * This method is used to retrieve order using userId
     *
     * @param user_id
     * @return List<UserOrderResponseDto>
     * @throws NotFound
     */
    @Override
    public List<UserOrderResponseDto> viewOrderByUserId(Integer user_id) throws NotFound {
        List<UserOrder> userOrder = userOrderRepo.findByUserId(user_id);
        if(!userOrder.isEmpty()) {
            List<UserOrderResponseDto> userOrderResponse = UserOrderMapper.getAllOrdersDto(userOrder);
            return userOrderResponse;
        } else {
            throw new NotFound("No Record Found!!!");
        }
    }

    /**
     * This method is used to Cancel the order
     *
     * @param order_id
     * @return SuccessDto
     * @throws NotFound
     */
    @Override
    public SuccessDto cancelOrderById(Integer order_id) throws NotFound {
        Integer isCancelled = userOrderRepo.cancelOrderbyId(order_id);
        if (isCancelled!= 0) {
            return new SuccessDto(202,"Order Cancelled Successfully");
        } else {
            throw new NotFound("Order not Cancelled!!!");
        }

    }

    /**
     * This method is used to retrieve all orders using productId
     *
     * @param productId
     * @return List<UserOrderResponseDto>
     * @throws NotFound
     */
   @Override
    public List<OrderDetailsResponseDto> viewOrdersByProductId(Integer productId) throws NotFound{
        List<OrderDetails> userOrders = userOrderRepo.findByProductId(productId);
        if(!userOrders.isEmpty()) {
            List<OrderDetailsResponseDto> orders = OrderDetailsMapper.getAllOrdersEntityToDto(userOrders);
            return orders;
        } else {
            throw new NotFound("No Record Found!!!");
        }

    }

    /**
     * This method is used for delivery person to get order by orderId
     * @param orderId
     * @return OrderDeliveryResponseDto
     * @throws NotFound
     */
    @Override
    public OrderDeliveryResponseDto getDeliveryOrder(Integer orderId) throws NotFound {
        OrderDelivery orderDelivery = orderDeliveryRepo.findByUserOrderId(orderId);
        if(orderDelivery != null) {
            return OrderDeliveryMapper.entityToDto(orderDelivery);
        } else {
            throw new NotFound("No Record Found");
        }
    }

    /**
     * This method is used retrieve all orders by date
     * @param orderedDate
     * @return
     * @throws NotFound
     */
    public List<UserOrderResponseDto> viewOrdersByDate(Date orderedDate) throws NotFound {
        List<UserOrder> userOrders =  userOrderRepo.findByOrderedDate(orderedDate);
        if(!userOrders.isEmpty()) {
            return UserOrderMapper.getAllOrdersDto(userOrders);
        } else {
            throw new NotFound("No Record Found");
        }

    }

    /**
     * This method is used to retrieve orders by ordered date and userId
     * @param orderedDate
     * @param userId
     * @return
     * @throws NotFound
     */
    @Override
    public List<UserOrderResponseDto> viewOrdersByIdAndDate(Date orderedDate, Integer userId) throws NotFound {
        List<UserOrder> userOrders =  userOrderRepo.findByOrderedDateAndUserId(orderedDate, userId);
        if(!userOrders.isEmpty()) {
            return UserOrderMapper.getAllOrdersDto(userOrders);
        } else {
            throw new NotFound("No Record Found");
        }
    }

}
