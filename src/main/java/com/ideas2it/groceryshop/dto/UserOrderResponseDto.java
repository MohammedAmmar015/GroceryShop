package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserOrderResponseDto {
    private Date orderedDate;
    private Float totalPrice;
    private List<OrderDetailsResponseDto> orderDetails;
    private OrderDeliveryResponseDto orderDeliveryResponseDto;
    private Boolean isActive;
    private Integer userId;
}
