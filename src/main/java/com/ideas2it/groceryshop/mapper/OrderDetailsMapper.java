package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.UserOrderRequestDto;
import com.ideas2it.groceryshop.dto.OrderDetailsResponseDto;
import com.ideas2it.groceryshop.model.OrderDetails;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsMapper {

    /**
     * This method is used to convert UserOrderRequestDto to OrderDetails Entity
     * @param orderDetailsRequestDto
     * @return OrderDetails
     */
    public static OrderDetails dtoToEntity(UserOrderRequestDto orderDetailsRequestDto) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setQuantity(orderDetailsRequestDto.getQuantity());
        return orderDetails;
    }

    /**
     * This method is used to convert OrderDetails to OrderDetailsResponseDto
     * @param orderDetails
     * @return OrderDetailsResponseDto
     */
    public static OrderDetailsResponseDto entityToDto(OrderDetails orderDetails){
        OrderDetailsResponseDto orderDetailsResponseDto = new OrderDetailsResponseDto();
        orderDetailsResponseDto.setQuantity(orderDetails.getQuantity());
        orderDetailsResponseDto.setPrice(orderDetails.getPrice());
        orderDetailsResponseDto.setProductId(orderDetails.getProduct().getId());
        orderDetailsResponseDto.setProductName(orderDetails.getProduct().getName());
        return orderDetailsResponseDto;
    }

    /**
     * This method is used to convert List<OrderDetails> to List<OrderDetailsResponseDto>
     * @param orderDescription
     * @return List<OrderDetailsResponseDto>
     */
    public static List<OrderDetailsResponseDto> getAllOrdersEntityToDto(List<OrderDetails> orderDescription) {
        List<OrderDetailsResponseDto> orderDetailsDtos = new ArrayList<>();
        for (OrderDetails orderDetail : orderDescription) {
            OrderDetailsResponseDto orderDetailsResponseDto = entityToDto(orderDetail);
            orderDetailsDtos.add(orderDetailsResponseDto);
        }
        return orderDetailsDtos;
    }

}
