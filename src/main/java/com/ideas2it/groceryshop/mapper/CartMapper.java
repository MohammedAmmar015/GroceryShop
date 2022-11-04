package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.CartDetailsResponse;
import com.ideas2it.groceryshop.dto.CartResponse;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.CartDetails;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {

    public static CartResponse convertCartToCartResponse(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());
        cartResponse.setTotalPrice(cart.getTotalPrice());
        cartResponse.setCreatedAt(cart.getCreatedAt());
        cartResponse.setModifiedAt(cart.getModifiedAt());
        List<CartDetailsResponse> cartDetailsResponse = new ArrayList<>();
        for (CartDetails cartDetails : cart.getCartDetails()) {
            cartDetailsResponse.add(CartMapper.convertCartDetailsToCartDetailsResponse(cartDetails));
        }
        cartResponse.setCartDetails(cartDetailsResponse);
        return cartResponse;
    }

    private static CartDetailsResponse convertCartDetailsToCartDetailsResponse(CartDetails cartDetails) {
        CartDetailsResponse cartDetailsResponse = new CartDetailsResponse();
        cartDetailsResponse.setId(cartDetails.getId());
        cartDetailsResponse.setCreatedAt(cartDetailsResponse.getCreatedAt());
        cartDetailsResponse.setModifiedAt(cartDetailsResponse.getModifiedAt());
        cartDetailsResponse.setProduct(cartDetailsResponse.getProduct());
        cartDetailsResponse.setQuantity(cartDetailsResponse.getQuantity());
        cartDetailsResponse.setQuantity(cartDetailsResponse.getQuantity());
        return cartDetailsResponse;
    }
}
