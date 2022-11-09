package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.User;
import com.ideas2it.groceryshop.repository.CartRepo;
import com.ideas2it.groceryshop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 *     Cart Helper
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
     * @throws NotFoundException - if cart not found by id
     */
    public Cart getCartById(Integer cartId, Boolean status) throws NotFoundException {
        Cart cart = cartService.getCartByCartId(cartId, status);
        return cart;
    }

    /**
     * <p>
     *     To delete cart details for Particular user's cart
     * </p>
     * @param user
     * @throws NotFoundException
     */
    public void deleteAllProductsFromCart(User user) throws NotFoundException {
         cartService.removeCart(user.getId());
    }
}
