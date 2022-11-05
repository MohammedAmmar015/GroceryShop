package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.OrderDetailsResponseDto;
import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.mapper.UserOrderMapper;
import com.ideas2it.groceryshop.model.*;
import com.ideas2it.groceryshop.repository.CartRepo;
import com.ideas2it.groceryshop.repository.OrderDetailsRepo;
import com.ideas2it.groceryshop.repository.UserOrderRepo;
import com.ideas2it.groceryshop.repository.UserRepo;
import com.ideas2it.groceryshop.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderServiceImpl implements UserOrderService {

    private final UserOrderRepo userOrderRepo;
    private final  CartRepo cartRepo;
    private final OrderDetailsRepo orderDetailsRepo;

    @Override
    public List<UserOrderResponseDto> viewAllActiveOrders() {
        List<UserOrder> orders = userOrderRepo.findByIsActive(true);
        List<UserOrderResponseDto> viewPlacedOrders = UserOrderMapper.getAllOrdersDto(orders);
        return viewPlacedOrders;
    }
    @Override
    public void placeOrder(Integer cartId) {
        Cart cart = cartRepo.findByIdAndIsActive(cartId, true);
        if(cart != null) {
            UserOrder userOrder = new UserOrder();
            userOrder.setCart(cart);
            userOrder.setUser(cart.getUser());
            userOrderRepo.save(userOrder);
        }
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
            return UserOrderMapper.entityToDto(userOrder.get());
        } else {
            return null;
        }
    }

//    public void viewAllProductOrders(Integer productId) {
//        ProductHelper productHelper = new ProductHelper();
//        Product product = productHelper.getProductById(productId);
//
//        return viewCancelledOrders;
//    }

}
