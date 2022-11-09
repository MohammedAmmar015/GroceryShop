package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.CartRequest;
import com.ideas2it.groceryshop.dto.CartResponse;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 *     Cart Controller for Cart related APIs
 * </p>
 * @author Mohammed Ammar
 * @since 04-11-2022
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private CartService cartService;

    /**
     * <p>
     *     This API is used to Add Products to Cart of Particular user
     * </p>
     * @param cartRequest - Cart details to be added to cart
     * @param userId - user's id to add product to user's cart
     * @return SuccessDto with Message and Status Code
     */
    @PostMapping("/{userId}")
    public SuccessDto createCart(@RequestBody CartRequest cartRequest,
                                 @PathVariable Integer userId) throws NotFoundException {
        return cartService.addCart(cartRequest, userId);
    }

    /**
     * <p>
     *     This API is used to view Cart for Particular user by user id
     * </p>
     * @param userId - user's id to view Cart
     * @return - CartResponse with product details
     */
    @GetMapping("/{userId}")
    public CartResponse viewCarts(@PathVariable Integer userId) throws NotFoundException {
        return cartService.getCartByUserId(userId);
    }

    /**
     * <p>
     *     This API is used to update cart product's quantity of Particular user
     * </p>
     * @param cartRequest - cart details to be updated
     * @param userId - user's id to update cart details
     */
    @PutMapping("/{userId}")
    public SuccessDto updateCart(@RequestBody CartRequest cartRequest,
                                @PathVariable Integer userId) {
        return cartService.updateCartByUser(cartRequest, userId);
    }

    /**
     * <p>
     *     This API is used to delete all products from Cart
     * </p>
     * @param userId user's id to delete all products from cart
     */
    @DeleteMapping("/{userId}")
    public SuccessDto deleteCart(@PathVariable Integer userId) throws NotFoundException {
        return cartService.removeCart(userId);
    }

    /**
     * <p>
     *     This API is used to delete Particular product from Cart
     * </p>
     * @param userId - user's id
     * @param productId - product id to delete from cart
     */
    @DeleteMapping("/{userId}/{productId}")
    public SuccessDto deleteProductFromCart(@PathVariable Integer userId,
                                            @PathVariable Integer productId) throws NotFoundException {
        return cartService.removeProductFromCart(userId,productId);
    }

}
