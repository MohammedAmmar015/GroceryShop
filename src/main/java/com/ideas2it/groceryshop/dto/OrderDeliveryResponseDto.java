package com.ideas2it.groceryshop.dto;

import com.ideas2it.groceryshop.model.Address;
import com.ideas2it.groceryshop.model.UserOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDeliveryResponseDto {
    private Boolean isDelivered;
    private Date deliveryDate;
    private Integer orderId;
    private AddressResponseDto shippingAddress;
}

