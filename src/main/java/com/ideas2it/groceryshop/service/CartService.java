package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CartRequest;
import com.ideas2it.groceryshop.dto.CartResponse;
import com.ideas2it.groceryshop.dto.SuccessDto;

/**
 * <p>
 *     Interface for Cart related Services
 * </p>
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
public interface CartService {

    /**
     * <p>
     *     It is used to add Product to the Cart
     * </p>
     * @param cartRequest - product details to add into Cart
     * @param userId - user's id to add product to user's cart
     * @return - successDto with Message and status Code
     */
    SuccessDto addCart(CartRequest cartRequest, Integer userId);

    /**
     * <p>
     *     It is used to get Cart of Particular user by Id
     * </p>
     * @param userId - user's id to get Cart
     * @return - CartResponse with cart details
     */
    CartResponse getCartByUserId(Integer userId);

    /**
     * <p>
     *     It is used To remove Cart details from Cart
     * </p>
     * @param userId - user's id to remove products from cart
     */
    void removeCart(Integer userId);

    /**
     * <p>
     *     It is used to remove particular product from cart
     * </p>
     * @param userId - user's id to remove product from cart
     * @param productId - product id to be removed
     */
    void removeProductFromCart(Integer userId, Integer productId);

    /**
     * <p>
     *     It is used to Update Cart Product Quantity of Particular user
     * </p>
     * @param cartRequest - cart details to be Updated
     * @param userId - user's id to update cart product
     */
    void updateCartByUser(CartRequest cartRequest, Integer userId);
}
