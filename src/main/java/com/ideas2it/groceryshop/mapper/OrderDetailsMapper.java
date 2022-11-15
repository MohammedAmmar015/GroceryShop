package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.OrderDetailsResponseDto;
import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * It is used to convert entity to dto and vice versa
 * @author Dhanalakshmi.M
 * @version 1.0
 */
public class OrderDetailsMapper {

    /**
     * This method is used to convert UserOrderRequestDto to OrderDetails Entity
     * @param orderDetailsRequest it contains quantity
     * @return OrderDetails it contains quantity
     */
    public static OrderDetails dtoToEntity(UserOrderRequestDto orderDetailsRequest) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setQuantity(orderDetailsRequest.getQuantity());
        return orderDetails;
    }

    /**
     * This method is used to convert OrderDetails to OrderDetailsResponseDto
     * @param orderDetails
     * @return OrderDetailsResponseDto
     */
    public static OrderDetailsResponseDto entityToDto(OrderDetails orderDetails){
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
     * This method is used to convert List<OrderDetails> to List<OrderDetailsResponseDto>
     * @param orderDescription
     * @return List<OrderDetailsResponseDto>
     */
    public static List<OrderDetailsResponseDto> getAllOrdersEntityToDto(List<OrderDetails> orderDescription) {
        List<OrderDetailsResponseDto> orderDetails = new ArrayList<>();
        for (OrderDetails orderDetail : orderDescription) {
            OrderDetailsResponseDto orderDetailsResponseDto = entityToDto(orderDetail);
            orderDetails.add(orderDetailsResponseDto);
        }
        return orderDetails;
    }

}
