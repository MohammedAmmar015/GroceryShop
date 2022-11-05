package com.ideas2it.groceryshop.dto;

import com.ideas2it.groceryshop.dto.OrderDetailsResponseDto;
import com.ideas2it.groceryshop.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserOrderResponseDto {
    private Date orderedDate;
    private Float totalPrice;
    private List<OrderDetailsResponseDto> orderDetailsResponseDtos;
    private User user;

}
