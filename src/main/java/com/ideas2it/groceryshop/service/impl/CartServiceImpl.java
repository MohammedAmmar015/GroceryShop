package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CartRequest;
import com.ideas2it.groceryshop.dto.CartResponse;
import com.ideas2it.groceryshop.mapper.CartMapper;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.CartDetails;
import com.ideas2it.groceryshop.repository.CartRepo;
import com.ideas2it.groceryshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 *     Cart Service class for Cart related Operations
 * </p>
 * @author Mohammed Ammar
 * @since 04-11-2022
 * @version 1.0
 */
@Service
public class CartServiceImpl implements CartService {
    private CartRepo cartRepo;
    @Autowired
    public CartServiceImpl(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    @Override
    public void addCart(CartRequest cartRequest) {
        //User user = cartRequest.getUserId();

    }
    @Override
    public CartResponse getCartByUserId(Integer userId) {
        Cart cart = cartRepo.findByUserIdAndIsActive(userId, true);
        return CartMapper.convertCartToCartResponse(cart);
    }

    @Override
    public void removeCart(Integer userId) {
        cartRepo.deleteCartByUserId(userId);
        /*Cart cart = cartRepo.findByUserIdAndIsActive(userId, true);
        cart.setIsActive(false);
        cartRepo.save(cart);*/
    }

    @Override
    public void removeProductFromCart(Integer userId, Integer productId) {
        Cart cart = cartRepo.findByUserIdAndIsActive(userId, true);
        for (CartDetails cartDetails : cart.getCartDetails()) {
            if (cartDetails.getProduct().getId() == productId) {
                cartDetails.setIsActive(false);
            }
        }
        cartRepo.save(cart);
    }
}
