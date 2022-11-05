package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartHelper {

    private CartRepo cartRepo;

    @Autowired
    public CartHelper(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public Cart getCartById(Integer cartId, Boolean status) {
        Cart cart = cartRepo.findByIdAndIsActive(cartId, status);
        return cart;
    }
}
