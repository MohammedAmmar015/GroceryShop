package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.CartRequest;
import com.ideas2it.groceryshop.dto.CartResponse;
import com.ideas2it.groceryshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *     Cart Controller for Cart related APIs
 * </p>
 * @author Mohammed Ammar
 * @since 04-11-2022
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{userId}")
    public void createCart(@RequestBody CartRequest cartRequest,
                           @PathVariable Integer userId) {
        cartService.addCart(cartRequest, userId);
    }

    @GetMapping("/{userId}")
    public CartResponse viewCarts(@PathVariable Integer userId) {
        return cartService.getCartByUserId(userId);
    }

    @PutMapping("/{userId}")
    public void updateCart(@RequestBody CartRequest cartRequest,
                           @PathVariable Integer userId) {
        cartService.updateCartByUser(cartRequest, userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteCart(@PathVariable Integer userId) {
        cartService.removeCart(userId);
    }

    @DeleteMapping("/{userId}/{productId}")
    public void deleteProductFromCart(@PathVariable Integer userId, @PathVariable Integer productId) {
        cartService.removeProductFromCart(userId,productId);
    }

}
