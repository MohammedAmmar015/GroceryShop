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
    private Integer userId;
    private Date orderedDate;
    private Float totalPrice;
    private List<OrderDetailsResponseDto> orderDetails;
    private Boolean isDelivered;

}
