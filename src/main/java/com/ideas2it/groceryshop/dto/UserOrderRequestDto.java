package com.ideas2it.groceryshop.dto;

import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.User;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderRequestDto {

    private Integer quantity;

    private Integer productId;

    private Date orderedDate;

}
