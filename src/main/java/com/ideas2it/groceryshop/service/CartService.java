package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CartRequest;
import com.ideas2it.groceryshop.dto.CartResponse;

public interface CartService {

    void addCart(CartRequest cartRequest, Integer userId);

    CartResponse getCartByUserId(Integer userId);

    void removeCart(Integer userId);

    void removeProductFromCart(Integer userId, Integer productId);

    void updateCartByUser(CartRequest cartRequest, Integer userId);
}
