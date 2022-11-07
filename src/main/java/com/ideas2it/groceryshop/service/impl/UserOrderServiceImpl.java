package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.helper.CartHelper;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.helper.UserHelper;
import com.ideas2it.groceryshop.mapper.OrderDeliveryMapper;
import com.ideas2it.groceryshop.mapper.UserOrderMapper;
import com.ideas2it.groceryshop.model.*;
import com.ideas2it.groceryshop.repository.CartRepo;
import com.ideas2it.groceryshop.repository.OrderDeliveryRepo;
import com.ideas2it.groceryshop.repository.OrderDetailsRepo;
import com.ideas2it.groceryshop.repository.UserOrderRepo;
import com.ideas2it.groceryshop.service.OrderDeliveryService;
import com.ideas2it.groceryshop.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderServiceImpl implements UserOrderService {

    private final UserOrderRepo userOrderRepo;
    private final  CartRepo cartRepo;
    private final OrderDetailsRepo orderDetailsRepo;
    private final OrderDeliveryRepo orderDeliveryRepo;
    private final CartHelper cartHelper;
    private final ProductHelper productHelper;
    private final UserHelper userHelper;

    @Override
    public void placeOrder(Integer cartId) {
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

        }
    }

    public List<OrderDetails> cartDetailsToOrderDetails(List<CartDetails> cartDetails) {
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

    @Override
    public void buyNow(UserOrderRequestDto userOrderRequestDto, Integer userId) {
        UserOrder userOrder = new UserOrder();
        OrderDetails orderDetail = new OrderDetails();
        List<OrderDetails> orderDetails = new ArrayList<>();
        Integer quantity = userOrderRequestDto.getQuantity();
        Integer productId = userOrderRequestDto.getProductId();
        Product product = productHelper.getProductById(productId);
        Float productPrice = product.getPrice();
        Float totalPrice = quantity*productPrice;
        userOrder.setTotalPrice(totalPrice);
        userOrder.setUser(userHelper.findUserById(userId));
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(totalPrice);
        orderDetail.setProduct(product);
        orderDetails.add(orderDetail);
        userOrder.setOrderDetails(orderDetails);
        userOrderRepo.save(userOrder);

    }

    @Override
    public List<UserOrderResponseDto> viewAllActiveOrders() {
        List<UserOrder> orders = userOrderRepo.findByIsActive(true);
        List<UserOrderResponseDto> viewPlacedOrders = UserOrderMapper.getAllOrdersDto(orders);
        return viewPlacedOrders;
    }

    @Override
    public List<UserOrderResponseDto> viewAllCancelledOrders() {
        List<UserOrder> orders = userOrderRepo.findByIsActive(false);
        List<UserOrderResponseDto> viewCancelledOrders = UserOrderMapper.getAllOrdersDto(orders);
        return viewCancelledOrders;
    }

    @Override
    public UserOrderResponseDto viewOrderById(Integer orderId) {
        Optional<UserOrder> userOrder = userOrderRepo.findById(orderId);
        if (userOrder.isPresent()) {
            System.out.println(userOrder.get()+""+"jerry");
            return UserOrderMapper.entityToDto(userOrder.get());
        } else {
            return null;
        }
    }

    @Override
    public List<UserOrderResponseDto> viewOrderByUserId(Integer user_id) {
        List<UserOrder> userOrder = userOrderRepo.findByUserId(user_id);
        if (userOrder!=null) {
            List<UserOrderResponseDto> userOrderResponse = UserOrderMapper.getAllOrdersDto(userOrder);
            return userOrderResponse;
        } else {
            return null;
        }

    }

    @Override
    public String cancelOrderById(Integer order_id) {
        Integer isCancelled = userOrderRepo.cancelOrderbyId(order_id);
        if (isCancelled!= 0) {
            return "Order Cancelled Successfully";
        } else {
            return "Not Cancelled";
        }

    }

   @Override
    public List<UserOrderResponseDto> viewOrdersByProductId(Integer productId) {
        List<UserOrder> userOrders = userOrderRepo.findByProductId(productId);
        List<UserOrderResponseDto> orders = UserOrderMapper.getAllOrdersDto(userOrders);
        if (orders==null) {
            System.out.println(orders + "" + "hlo");
        }
            return orders;
    }

    @Override
    public List<OrderDeliveryResponseDto> pendingOrder(Integer orderId) {
        List<OrderDelivery> orderDelivery = orderDeliveryRepo.findByOrderIdAndIsActive(true, orderId);
        return OrderDeliveryMapper.getAllOrdersDto(orderDelivery);

    }

//    public List<UserOrderResponseDto> viewOrdersByDate(Date orderedDate) {
//        Timestamp ts=new Timestamp(orderedDate.getTime());
//        List<UserOrder> userOrders= userOrderRepo.findByOrderedDate(orderedDate);
//        System.out.println(userOrders);
//        List<UserOrderResponseDto> userOrderResponseDtos = UserOrderMapper.getAllOrdersDto(userOrders);
//        return userOrderResponseDtos;
//    }

}
