/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.CartService;
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
 *     Cart Rest Controller for Cart related APIs,
 *     1. To add product to Cart
 *     2. To view Cart
 *     3. To Delete Cart
 *     4. To Delete Product from Cart
 *     5. To Update quantity in Cart
 * </p>
 * @author Mohammed Ammar
 * @since 04-11-2022
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final Logger logger;
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.logger = LogManager.getLogger(CartController.class);
        this.cartService = cartService;
    }

    /**
     * <p>
     *     This POST API api/v1/carts
     *     is used to Add Product
     *     to Cart of currently logged-in user
     * </p>
     * @param cartRequest - Cart details to be added to cart
     * @return SuccessResponseDto with Message and Status Code
     * @throws NotFound if Cart or Product Not found
     * @throws Existed if product already exist in Cart
     */
    @PostMapping
    public SuccessResponseDto createCart(@Valid @RequestBody CartRequestDto cartRequest)
                                            throws NotFound, Existed {
        logger.debug("Entered createCart method in CartController");
        return cartService.addCart(cartRequest);
    }

    /**
     * <p>
     *     This GET API api/v1/carts
     *     is used to view Cart of Currently logged-in user
     * </p>
     * @return - CartResponse with product details
     * @throws NotFound if Cart not found
     */
    @GetMapping
    public CartResponseDto viewCart() throws NotFound {
        logger.debug("Entered viewCart method in CartController");
        return cartService.getCart();
    }

    /**
     * <p>
     *     This PUT API api/v1/carts
     *     is used to update cart product's quantity of currently
     *     logged-in user
     * </p>
     * @param cartRequest - cart details to be updated
     * @return SuccessResponseDto if cart updated successfully
     * @throws NotFound if cart not found
     */
    @PutMapping
    public SuccessResponseDto updateCart(@Valid @RequestBody CartRequestDto cartRequest)
                                        throws NotFound {
        logger.debug("Entered updateCart method in CartController");
        return cartService.updateCartByUser(cartRequest);
    }

    /**
     * <p>
     *     This DELETE API api/v1/carts
     *     is used to delete all products from Cart
     *     based on currently logged-in user
     * </p>
     * @return SuccessResponseDto if cart deleted successfully
     * @throws NotFound if Cart not found
     */
    @DeleteMapping
    public SuccessResponseDto deleteCart() throws NotFound {
        logger.debug("Entered deleteCart method in CartController");
        return cartService.removeCart();
    }

    /**
     * <p>
     *     This DELETE API api/v1/carts/{productId}
     *     is used to delete Particular product from Cart
     *     based on productId and currently logged-in user
     * </p>
     * @param productId - product id to delete from cart
     * @return SuccessResponseDto if product from cart deleted successfully
     * @throws NotFound if cart not found
     */
    @DeleteMapping("/{productId}")
    public SuccessResponseDto deleteProductFromCart(@PathVariable Integer productId)
                                                    throws NotFound {
        logger.debug("Entered deleteProductFromCart method in CartController");
        return cartService.removeProductFromCart(productId);
    }

}