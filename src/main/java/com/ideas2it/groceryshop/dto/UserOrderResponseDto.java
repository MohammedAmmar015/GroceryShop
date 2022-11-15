package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * It is a user order response dto which is used for showing response to the user
 * @author Dhanalakshmi.M
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserOrderResponseDto {

    private Integer userId;

    private Date orderedDate;

    private Date expectedDeliveryDate;

    private Float totalPrice;

    private Integer totalQuantity;

    private List<OrderDetailsResponseDto> orderDetails;

    private Boolean isDelivered;

}
