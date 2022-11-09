package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.model.Cart;

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
<<<<<<< Updated upstream
    SuccessDto addCart(CartRequest cartRequest, Integer userId) throws NotFound;
=======
    SuccessDto addCart(CartRequestDto cartRequest, Integer userId) throws NotFoundException;
>>>>>>> Stashed changes

    /**
     * <p>
     *     It is used to get Cart of Particular user by Id
     * </p>
     * @param userId - user's id to get Cart
     * @return - CartResponse with cart details
     */
<<<<<<< Updated upstream
    CartResponse getCartByUserId(Integer userId) throws NotFound;
=======
    CartResponseDto getCartByUserId(Integer userId) throws NotFoundException;
>>>>>>> Stashed changes

    /**
     * <p>
     * It is used To remove Cart details from Cart
     * </p>
     *
     * @param userId - user's id to remove products from cart
     * @return
     */
    SuccessDto removeCart(Integer userId) throws NotFound;

    /**
     * <p>
     * It is used to remove particular product from cart
     * </p>
     *
     * @param userId    - user's id to remove product from cart
     * @param productId - product id to be removed
     * @return
     */
    SuccessDto removeProductFromCart(Integer userId, Integer productId) throws NotFound;

    /**
     * <p>
     * It is used to Update Cart Product Quantity of Particular user
     * </p>
     *
     * @param cartRequest - cart details to be Updated
     * @param userId      - user's id to update cart product
     * @return
     */
    SuccessDto updateCartByUser(CartRequestDto cartRequest, Integer userId);

    Cart getCartByCartId(Integer cartId, Boolean status) throws NotFound;
}
