package com.ideas2it.groceryshop.dto;

import com.ideas2it.groceryshop.model.StoreLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {

    private Integer id;

    private Integer availableStock;

    private String productName;

    private Integer productId;

    private Date modifiedAt;

    private Integer modifiedBy;

    private String location;
}

