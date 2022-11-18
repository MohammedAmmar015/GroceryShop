/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * Cart Rest Controller for Cart related APIs,
 * 1. api to add product to user's Cart
 * 2. api to view user's Cart
 * 3. api to Delete user's Cart
 * 4. api to Delete Product from user's Cart
 * 5. api to Update quantity in user's Cart
 * </p>
 *
 * @author Mohammed Ammar
 * @version 1.0
 * @since 04-11-2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/carts")
public class CartController {
    private final Logger logger = LogManager.getLogger(CartController.class);
    private final CartService cartService;


    /**
     * <p>
     * This POST API {api/v1/user/carts}
     * is used to Add Product
     * to Cart of currently logged-in user
     * </p>
     *
     * @param cartRequest - Cart details to be added to cart
     * @return SuccessResponseDto with success Message and Status Code
     * @throws NotFoundException if Cart or Product Not found
     * @throws ExistedException  if product already exist in Cart
     */
    @PostMapping
    public SuccessResponseDto createCart(@Valid @RequestBody CartRequestDto cartRequest)
                                        throws NotFoundException, ExistedException {
        logger.debug("Entered createCart method in CartController");
        return cartService.addCart(cartRequest);
    }

    /**
     * <p>
     * This GET API {api/v1/user/carts}
     * is used to view Cart of Currently logged-in user
     * </p>
     *
     * @return - CartResponse with product details
     * @throws NotFoundException if Cart not found
     */
    @GetMapping
    public CartResponseDto viewCart() throws NotFoundException {
        logger.debug("Entered viewCart method in CartController");
        return cartService.getCart();
    }

    /**
     * <p>
     * This PUT API {api/v1/user/carts}
     * is used to update cart product's quantity of currently
     * logged-in user
     * </p>
     *
     * @param cartRequest - cart details to be updated
     * @return SuccessResponseDto if cart updated successfully
     * @throws NotFoundException if cart not found
     */
    @PutMapping
    public SuccessResponseDto updateCart(@Valid @RequestBody CartRequestDto cartRequest)
                                         throws NotFoundException {
        logger.debug("Entered updateCart method in CartController");
        return cartService.updateCartByUser(cartRequest);
    }

    /**
     * <p>
     * This DELETE API {api/v1/user/carts}
     * is used to delete all products from Cart
     * based on currently logged-in user
     * </p>
     *
     * @return SuccessResponseDto if cart deleted successfully
     * @throws NotFoundException if Cart not found
     */
    @DeleteMapping
    public SuccessResponseDto deleteCart() throws NotFoundException {
        logger.debug("Entered deleteCart method in CartController");
        return cartService.removeCart();
    }

    /**
     * <p>
     * This DELETE API {api/v1/user/carts/products/{productId}}
     * is used to delete Particular product from Cart
     * based on productId and currently logged-in user
     * </p>
     *
     * @param productId - product id to delete from cart
     * @return SuccessResponseDto if product from cart deleted successfully
     * @throws NotFoundException if cart not found
     */
    @DeleteMapping("/products/{productId}")
    public SuccessResponseDto deleteProductFromCart(@PathVariable Integer productId)
                                                    throws NotFoundException {
        logger.debug("Entered deleteProductFromCart method in CartController");
        return cartService.removeProductFromCart(productId);
    }

}