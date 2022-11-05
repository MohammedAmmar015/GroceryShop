package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.CartDetailsRequest;
import com.ideas2it.groceryshop.dto.CartDetailsResponse;
import com.ideas2it.groceryshop.dto.CartRequest;
import com.ideas2it.groceryshop.dto.CartResponse;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.CartDetails;
import com.ideas2it.groceryshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {

    public static CartResponse convertCartToCartResponse(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());
        cartResponse.setTotalPrice(cart.getTotalPrice());
        cartResponse.setCreatedAt(cart.getCreatedAt());
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
        cartDetailsResponse.setCreatedAt(cartDetails.getCreatedAt());
        cartDetailsResponse.setProductName(cartDetails.getProduct().getName());
        cartDetailsResponse.setQuantity(cartDetails.getQuantity());
        cartDetailsResponse.setPrice(cartDetails.getPrice());
        return cartDetailsResponse;
    }

    public static Cart convertCartRequestToCart(CartRequest cartRequest) {
        Cart cart = new Cart();
        List<CartDetails> cartDetails = new ArrayList<>();
        CartDetailsRequest cartDetailsRequest = cartRequest.getCartDetails();
        cartDetails.add(CartDetailsMapper.toCartDetails(cartDetailsRequest));
        return cart;
    }
}
