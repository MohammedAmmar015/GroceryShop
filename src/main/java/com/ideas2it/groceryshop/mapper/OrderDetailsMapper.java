/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.groceryshop.dto.OrderRequestDto;
import com.ideas2it.groceryshop.dto.OrderDetailsResponseDto;
import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.Product;

/**
 * <p>
 *     Converts entity to dto and vice versa
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
public class OrderDetailsMapper {

    /**
     * <p>
     *     Converts UserOrderRequestDto to OrderDetails
     * </p>
     *
     * @param orderDetailsRequest - Contains quantity, product
     * @return OrderDetails - Contains quantity, product
     */
    public static OrderDetails toOrderDetails(OrderRequestDto orderDetailsRequest) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setQuantity(orderDetailsRequest.getQuantity());
        return orderDetails;
    }

    /**
     * <p>
     *     Converts OrderDetails to OrderDetailsResponseDto
     * </p>
     *
     * @param orderDetails - contains quantity, price and product
     * @return OrderDetailsResponseDto - contains quantity, price, productName,
     *                                   categoryName, subCategoryName
     */
    public static OrderDetailsResponseDto toOrderDetailsDto(OrderDetails orderDetails) {
        OrderDetailsResponseDto orderDetailsResponse = new OrderDetailsResponseDto();
        Product product = orderDetails.getProduct();
        orderDetailsResponse.setQuantity(orderDetails.getQuantity());
        orderDetailsResponse.setPrice(orderDetails.getPrice());
        orderDetailsResponse.setProductName(orderDetails.getProduct().getName());
        orderDetailsResponse.setCategoryName(product.getCategory().getCategory().getName());
        orderDetailsResponse.setSubCategoryName(product.getCategory().getName());
        return orderDetailsResponse;
    }

    /**
     * <p>
     *     Converts list of OrderDetails to list of OrderDetailsResponseDto
     * </p>
     *
     * @param orderDescription - list of orderDetails which contains quantity, price and product
     * @return OrderDetailsResponseDto - list of OrderDetailsResponse
     *                                   which contains quantity, price and product
     */
    public static List<OrderDetailsResponseDto> toOrderDetailsDtoList
                                                (List<OrderDetails> orderDescription) {
        List<OrderDetailsResponseDto> orderDetails = new ArrayList<>();
        for (OrderDetails orderDetail : orderDescription) {
            OrderDetailsResponseDto orderDetailsResponseDto = toOrderDetailsDto(orderDetail);
            orderDetails.add(orderDetailsResponseDto);
        }
        return orderDetails;
    }

}
