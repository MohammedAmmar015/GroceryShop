package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.CartDetailsRequestDto;
import com.ideas2it.groceryshop.dto.CartDetailsResponseDto;
import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.CartDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Cart Mapper, used to convert Cart Entity and Cart DTO
 * </p>
 * @author Mohammed Ammar
 * @since 02-11-2022
 * @version 1.0
 */
public class CartMapper {

    /**
     * <p>
     *     It is used to convert Cart Entity to CartResponse
     * </p>
     * @param cart - cart object
     * @return - CartResponse
     */
    public static CartResponseDto convertCartToCartResponse(Cart cart) {
        CartResponseDto cartResponse = new CartResponseDto();
        cartResponse.setId(cart.getId());
        cartResponse.setTotalPrice(cart.getTotalPrice());
        cartResponse.setCreatedAt(cart.getCreatedAt());
        List<CartDetailsResponseDto> cartDetailsResponse = new ArrayList<>();
        for (CartDetails cartDetails : cart.getCartDetails()) {
            cartDetailsResponse.add(CartDetailsMapper.convertCartDetailsToCartDetailsResponse(cartDetails));
        }
        cartResponse.setCartDetails(cartDetailsResponse);
        return cartResponse;
    }

    /**
     * <p>
     *     It is used to convert CartRequest to Cart object
     * </p>
     * @param cartRequest - cart request dto
     * @return - Cart
     */
    public static Cart convertCartRequestToCart(CartRequestDto cartRequest) {
        Cart cart = new Cart();
        List<CartDetails> cartDetails = new ArrayList<>();
        CartDetailsRequestDto cartDetailsRequest = cartRequest.getCartDetails();
        cartDetails.add(CartDetailsMapper.toCartDetails(cartDetailsRequest));
        return cart;
    }
}
