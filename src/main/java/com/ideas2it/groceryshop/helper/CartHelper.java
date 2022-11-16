/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.User;
import com.ideas2it.groceryshop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 *     Cart Helper, It has Common methods related to Cart
 * </p>
 * @author Mohammed Ammar
 * @since 03-11-2022
 * @version 1.0
 */
@AllArgsConstructor
@Service
public class CartHelper {

    private CartService cartService;

    /**
     * <p>
     *     It is used to get Cart by Cart Id
     * </p>
     * @param cartId cart id
     * @param status - is deleted or not
     * @return Cart
     * @throws NotFound - if cart not found by id
     */
    public Cart getCartById(Integer cartId, Boolean status) throws NotFound {
        return cartService.getCartByCartId(cartId, status);
    }

    /**
     * <p>
     *     To delete cart details for Particular user's cart
     * </p>
     * @param user user details to delete user's cart
     * @throws NotFound if cart not found for given user
     */
    public void deleteAllProductsFromCart(User user) throws NotFound {
         cartService.removeCart(user.getId());
    }
}
