package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CartRequest;
import com.ideas2it.groceryshop.dto.CartResponse;

public interface CartService {

    void addCart(CartRequest cartRequest);

    CartResponse getCartByUserId(Integer userId);

    void removeCart(Integer userId);

    void removeProductFromCart(Integer userId, Integer productId);

}