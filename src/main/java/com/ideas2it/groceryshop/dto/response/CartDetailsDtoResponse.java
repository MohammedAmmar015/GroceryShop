package com.ideas2it.groceryshop.dto.response;

import com.ideas2it.groceryshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDetailsDtoResponse {
    private Integer id;
    private Integer quantity;

    private Float price;

    private Product product;

    private Date createdAt;

    private Date modifiedAt;

    private Integer createdBy;

    private Integer modifiedBy;

    private Boolean isActive;
}
