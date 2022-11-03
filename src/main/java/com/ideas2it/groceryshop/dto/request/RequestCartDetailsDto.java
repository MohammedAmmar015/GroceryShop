package com.ideas2it.groceryshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestCartDetailsDto {
    private Integer quantity;

    private Float price;

    private Integer productId;
}
