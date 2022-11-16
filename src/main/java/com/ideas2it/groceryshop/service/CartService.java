/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.model.Cart;

/**
 * <p>
 *     Interface for Cart related Services
 *     This class is used to add, remove,
 *     update products from cart
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
     * @return - SuccessResponseDto with Message and status Code
     * @throws NotFound - if user or cart not found
     * @throws Existed if product already exist in cart
     */
    SuccessResponseDto addCart(CartRequestDto cartRequest, Integer userId) throws NotFound, Existed;


    /**
     * <p>
     *     It is used to get Cart of Particular user by Id
     * </p>
     * @param userId - user's id to get Cart
     * @return - CartResponse with cart details
     * @throws NotFound if cart not found
     */
    CartResponseDto getCartByUserId(Integer userId) throws NotFound;

    /**
     * <p>
     * It is used To remove Cart details from Cart
     * </p>
     *
     * @param userId - user's id to remove products from cart
     * @return SuccessResponseDto if cart deleted successfully
     * @throws NotFound throws if cart not found
     */
    SuccessResponseDto removeCart(Integer userId) throws NotFound;

    /**
     * <p>
     *     It is used to remove particular product from cart
     * </p>
     *
     * @param userId    - user's id to remove product from cart
     * @param productId - product id to be removed
     * @return SuccessResponseDto if product deleted from cart
     * @throws NotFound if cart or product not found
     */
    SuccessResponseDto removeProductFromCart(Integer userId, Integer productId) throws NotFound;

    /**
     * <p>
     * It is used to Update Cart Product Quantity of Particular user
     * </p>
     *
     * @param cartRequest - cart details to be Updated
     * @param userId      - user's id to update cart product
     * @return SuccessResponseDto if cart updated successfully
     * @throws NotFound if cart or product not found
     */
    SuccessResponseDto updateCartByUser(CartRequestDto cartRequest, Integer userId) throws NotFound;

    /**
     * <p>
     *     This method is used to get Cart by Cart Id
     * </p>
     * @param cartId - cart id to be retrieved
     * @param status - true or false
     * @return Cart
     * @throws NotFound - throws exception if cart not found
     */
    Cart getCartByCartId(Integer cartId, Boolean status) throws NotFound;
}
