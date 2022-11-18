/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
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
     * @return - SuccessResponseDto with Message and status Code
     * @throws NotFoundException - if user or cart not found
     * @throws ExistedException if product already exist in cart
     */
    SuccessResponseDto addOrModifyCart(CartRequestDto cartRequest)
                                      throws NotFoundException, ExistedException;

    /**
     * <p>
     *     It is used to get Cart of Particular user by Id
     * </p>
     * @return - CartResponse with cart details
     * @throws NotFoundException if cart not found
     */
    CartResponseDto getCart() throws NotFoundException;

    /**
     * <p>
     * It is used To remove Cart details from Cart
     * </p>
     *
     * @return SuccessResponseDto if cart deleted successfully
     * @throws NotFoundException throws if cart not found
     */
    SuccessResponseDto removeCart() throws NotFoundException;

    /**
     * <p>
     *     It is used to remove particular product from cart
     * </p>
     *
     * @param productId - product id to be removed
     * @return SuccessResponseDto if product deleted from cart
     * @throws NotFoundException if cart or product not found
     */
    SuccessResponseDto removeProductFromCart(Integer productId) throws NotFoundException;

    /**
     * <p>
     *     It is used to Update Cart Product Quantity of Particular user
     * </p>
     *
     * @param cartRequest - cart details to be Updated
     * @return SuccessResponseDto if cart updated successfully
     * @throws NotFoundException if cart or product not found
     */
    SuccessResponseDto updateCartByUser(CartRequestDto cartRequest) throws NotFoundException;

    /**
     * <p>
     *     This method is used to get active
     *     cart of currently logged-in user
     * </p>
     * @return Cart
     * @throws NotFoundException - throws exception if cart not found
     */
    Cart getActiveCartOfCurrentUser() throws NotFoundException;
}
