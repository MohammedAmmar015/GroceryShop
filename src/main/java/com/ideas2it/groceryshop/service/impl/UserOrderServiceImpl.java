package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.helper.CartHelper;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.helper.UserHelper;
import com.ideas2it.groceryshop.mapper.UserOrderMapper;
import com.ideas2it.groceryshop.model.*;
import com.ideas2it.groceryshop.repository.AddressRepo;
import com.ideas2it.groceryshop.repository.OrderDeliveryRepo;
import com.ideas2it.groceryshop.repository.UserOrderRepo;
import com.ideas2it.groceryshop.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *     This class acts like a intermediate between UserOrderController and Repository.
 * </p>
 * @author Dhanalakshmi
 * @version 1.0v
 */
@Service
@RequiredArgsConstructor
public class UserOrderServiceImpl implements UserOrderService {

    private final UserOrderRepo userOrderRepo;
    private final OrderDeliveryRepo orderDeliveryRepo;
    private final AddressRepo addressRepo;
    private final CartHelper cartHelper;
    private final ProductHelper productHelper;
    private final UserHelper userHelper;

    /**
     * <p>
     *     This method is used to placeOrder by using a cartId
     * </p>
     * @param userOrderRequestDto
     * @param cartId
     */
    @Override
    public void placeOrder(UserOrderRequestDto userOrderRequestDto, Integer cartId) {
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
            orderDelivery(userOrderRequestDto, userOrder);
        }
    }

    /**
     * <p>
     *     This method is used to add orderDelivery details
     * </p>
     * @param userOrderRequestDto
     * @param userOrder
     */
    private void orderDelivery(UserOrderRequestDto userOrderRequestDto, UserOrder userOrder) {
        OrderDelivery orderDelivery = new OrderDelivery();
        Optional<Address> address = addressRepo.findById(userOrderRequestDto.getAddressId());
        orderDelivery.setUserOrder(userOrder);
        orderDelivery.setShippingAddress(address.get());
        orderDeliveryRepo.save(orderDelivery);
    }

    /**
     * <p>
     *     This method is used for converting List<CartDetails> to List<OrderDetails>
     * </p>
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
     * <p>
     *     This method is used for placing order directly without cart
     * </p>
     * @param userOrderRequestDto
     * @param userId
     */
    @Override
    public void buyNow(UserOrderRequestDto userOrderRequestDto, Integer userId) {
        UserOrder userOrder = new UserOrder();
        List<OrderDetails> orderDetails = setOrderDetails(userOrderRequestDto);
        userOrder.setOrderDetails(orderDetails);
        userOrder.setTotalPrice(orderDetails.get(0).getPrice());
        userOrder.setUser(userHelper.findUserById(userId).get());
        userOrderRepo.save(userOrder);
        orderDelivery(userOrderRequestDto, userOrder);
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
     * <p>
     *     This method is used to retrieve all active orders
     * </p>
     * @return List<UserOrderResponseDto>
     */
    @Override
    public List<UserOrderResponseDto> viewAllActiveOrders() {
        List<UserOrder> orders = userOrderRepo.findByIsActive(true);
        List<UserOrderResponseDto> viewPlacedOrders = UserOrderMapper.getAllOrdersDto(orders);
        return viewPlacedOrders;
    }

    /**
     * <p>
     *     This method is used to retrieve all the cancelled order
     * </p>
     * @return List<UserOrderResponseDto>
     */
    @Override
    public List<UserOrderResponseDto> viewAllCancelledOrders() {
        List<UserOrder> orders = userOrderRepo.findByIsActive(false);
        List<UserOrderResponseDto> viewCancelledOrders = UserOrderMapper.getAllOrdersDto(orders);
        return viewCancelledOrders;
    }

    /**
     * <p>
     *     This method is used to retrieve order by using orderId
     * </p>
     * @param orderId
     * @return UserOrderResponseDto
     */
    @Override
    public UserOrderResponseDto viewOrderById(Integer orderId) {
        Optional<UserOrder> userOrder = userOrderRepo.findById(orderId);
        return UserOrderMapper.entityToDto(userOrder.get());
    }

    /**
     * <p>
     *     This method is used to retrieve order using userId
     * </p>
     * @param user_id
     * @return List<UserOrderResponseDto>
     */
    @Override
    public List<UserOrderResponseDto> viewOrderByUserId(Integer user_id) {
        List<UserOrder> userOrder = userOrderRepo.findByUserId(user_id);
        List<UserOrderResponseDto> userOrderResponse = UserOrderMapper.getAllOrdersDto(userOrder);
        return userOrderResponse;
    }

    /**
     * <p>
     *     This method is used to Cancel the order
     * </p>
     * @param order_id
     * @return String
     */
    @Override
    public String cancelOrderById(Integer order_id) {
        Integer isCancelled = userOrderRepo.cancelOrderbyId(order_id);
        if (isCancelled!= 0) {
            return "Order Cancelled Successfully";
        } else {
            return "Not Cancelled";
        }

    }

    /**
     * <p>
     *     This method is used to retrieve all orders using productId
     * </p>
     * @param productId
     * @return List<UserOrderResponseDto>
     */
   @Override
    public List<UserOrderResponseDto> viewOrdersByProductId(Integer productId) {
        List<UserOrder> userOrders = userOrderRepo.findByProductId(productId);
        List<UserOrderResponseDto> orders = UserOrderMapper.getAllOrdersDto(userOrders);
        return orders;
    }

}
