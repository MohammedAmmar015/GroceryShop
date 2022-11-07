package com.ideas2it.groceryshop.dto;

import com.ideas2it.groceryshop.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailsResponseDto {

    private Integer quantity;

    private Float price;

    private Integer productId;

    private String productName;


}
