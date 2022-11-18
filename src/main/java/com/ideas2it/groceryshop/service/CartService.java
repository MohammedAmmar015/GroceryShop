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
     *     This method is used to add Product to the existing Cart
     *     or create a new cart if cart is inactive
     *     for particular user based on currently logged-in user
     *     and also cart details will be added to cart by cartRequest
     *     which has product id and quantity
     * </p>
     * @param cartRequest - product id and quantity to add into Cart
     * @return - SuccessResponseDto with Message and status Code if product
     *          successfully added to cart
     * @throws NotFoundException - if given product id not found
     * @throws ExistedException if product already exist in cart
     */
    SuccessResponseDto addOrModifyCart(CartRequestDto cartRequest)
                                      throws NotFoundException, ExistedException;

    /**
     * <p>
     *     This method is used to get Cart of currently
     *     logged-in user and to return cart details
     *     as response DTO
     * </p>
     * @return - CartResponse with products and it's total price
     *           that added to cart by user
     * @throws NotFoundException if cart is inActive or not found
     */
    CartResponseDto getCart() throws NotFoundException;

    /**
     * <p>
     *     This method is used to remove all products from Cart
     *     and to delete cart
     *     of currently logged-in user
     * </p>
     *
     * @return SuccessResponseDto with success message and status code
     *          if cart deleted successfully
     */
    SuccessResponseDto removeCart() throws NotFoundException;

    /**
     * <p>
     *     This method is used to remove particular product
     *     based on product id,
     *     from user's cart of currently logged-in user
     *     and to update total price based
     *     on current cart details
     * </p>
     *
     * @param productId - product id to be removed
     * @return SuccessResponseDto if product deleted from cart
     * @throws NotFoundException if cart or product not found
     */
    SuccessResponseDto removeProductFromCart(Integer productId) throws NotFoundException;

    /**
     * <p>
     *     This method is used to update product quantity in user's Cart
     *     of currently logged-in user
     *     CartRequest will have product id and quantity to be updated,
     *     new totalPrice and totalQuantity will also get updated
     * </p>
     *
     * @param cartRequest - cart details to be Updated
     * @return SuccessResponseDto if cart updated successfully
     * @throws NotFoundException if cart or product not found
     */
    SuccessResponseDto updateCartByUser(CartRequestDto cartRequest) throws NotFoundException;

    /**
     * <p>
     *     This method is used to get active cart
     *     of currently logged-in user
     * </p>
     * @return Cart if cart available else it will return null
     */
    Cart getActiveCartOfCurrentUser();
}
