package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.CartDetailsRequest;
import com.ideas2it.groceryshop.dto.CartDetailsResponse;
import com.ideas2it.groceryshop.model.CartDetails;

/**
 * <p>
 *     Cart Details Mapper, used to convert CartDetails Entity and CartDetails DTO
 * </p>
 * @author Mohammed Ammar
 * @since 02-11-2022
 * @version 1.0
 */
public class CartDetailsMapper {

    /**
     * <p>
     *     It is used to convert CartDetailsRequest to CartDetails
     * </p>
     * @param cartDetailsRequest - cart details
     * @return - CartDetails
     */
    public static CartDetails toCartDetails(CartDetailsRequest cartDetailsRequest) {
        CartDetails cartDetails = new CartDetails();
        cartDetails.setQuantity(cartDetailsRequest.getQuantity());
        return cartDetails;
    }

    /**
     * <p>
     *     It is used to convert CartDetails to CartDetailsResponse
     * </p>
     * @param cartDetails - cart details
     * @return - CartDetailsResponse
     */
    public static CartDetailsResponse convertCartDetailsToCartDetailsResponse(CartDetails cartDetails) {
        CartDetailsResponse cartDetailsResponse = new CartDetailsResponse();
        cartDetailsResponse.setId(cartDetails.getId());
        cartDetailsResponse.setCreatedAt(cartDetails.getCreatedAt());
        cartDetailsResponse.setProductName(cartDetails.getProduct().getName());
        cartDetailsResponse.setQuantity(cartDetails.getQuantity());
        cartDetailsResponse.setPrice(cartDetails.getPrice());
        return cartDetailsResponse;
    }
}
