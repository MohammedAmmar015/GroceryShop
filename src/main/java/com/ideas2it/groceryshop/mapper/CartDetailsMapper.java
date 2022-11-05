package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.CartDetailsRequest;
import com.ideas2it.groceryshop.model.CartDetails;

public class CartDetailsMapper {
    public static CartDetails toCartDetails(CartDetailsRequest cartDetailsRequest) {
        CartDetails cartDetails = new CartDetails();
        cartDetails.setQuantity(cartDetailsRequest.getQuantity());
        return cartDetails;
    }
}
