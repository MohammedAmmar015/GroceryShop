package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private CartService cartService;

    /**
     * <p>
     *     This POST API api/v1/carts/{userId}
     *     is used to Add Products
     *     to Cart of Particular user
     * </p>
     * @param cartRequest - Cart details to be added to cart
     * @param userId - user's id to add product to user's cart
     * @return SuccessDto with Message and Status Code
     * @throws NotFound if Cart or Product Not found
     * @throws Existed if product already exist in Cart
     */
    @PostMapping("/{userId}")
    public SuccessDto createCart(@Valid @RequestBody CartRequestDto cartRequest,
                                 @PathVariable Integer userId) throws NotFound, Existed {
        return cartService.addCart(cartRequest, userId);
    }

    /**
     * <p>
     *     This GET API api/v1/carts/{userId}
     *     is used to view Cart for Particular user
     *     based on user id
     * </p>
     * @param userId - user's id to view Cart
     * @return - CartResponse with product details
     * @throws NotFound if Cart not found
     */
    @GetMapping("/{userId}")
    public CartResponseDto viewCarts(@PathVariable Integer userId) throws NotFound {
        return cartService.getCartByUserId(userId);
    }

    /**
     * <p>
     *     This PUT API api/v1/carts/{userId}
     *     is used to update cart product's quantity of Particular user
     *     based on userId
     * </p>
     * @param cartRequest - cart details to be updated
     * @param userId - user's id to update cart details
     * @return successDto if cart updated successfully
     * @throws NotFound if cart not found
     */
    @PutMapping("/{userId}")
    public SuccessDto updateCart(@Valid @RequestBody CartRequestDto cartRequest,
                                @PathVariable Integer userId) throws NotFound {
        return cartService.updateCartByUser(cartRequest, userId);
    }

    /**
     * <p>
     *     This DELETE API api/v1/carts/{userId}
     *     is used to delete all products from Cart
     *     based on userId
     * </p>
     * @param userId user's id to delete all products from cart
     * @return successDto if cart deleted successfully
     * @throws NotFound if Cart not found
     */
    @DeleteMapping("/{userId}")
    public SuccessDto deleteCart(@PathVariable Integer userId) throws NotFound {
        return cartService.removeCart(userId);
    }

    /**
     * <p>
     *     This DELETE API api/v1/carts/{userId}/{productId}
     *     is used to delete Particular product from Cart
     *     based on productId and userId.
     * </p>
     * @param userId - user's id
     * @param productId - product id to delete from cart
     * @return successDto if product from cart deleted successfully
     * @throws NotFound if cart not found
     */
    @DeleteMapping("/{userId}/{productId}")
    public SuccessDto deleteProductFromCart(@PathVariable Integer userId,
                                            @PathVariable Integer productId) throws NotFound {
        return cartService.removeProductFromCart(userId,productId);
    }

}
