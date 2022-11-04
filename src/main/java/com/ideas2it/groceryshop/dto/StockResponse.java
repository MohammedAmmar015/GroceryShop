package com.ideas2it.groceryshop.dto;

import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.StoreLocation;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Date;

public class StockResponse {

    private Integer id;

    private Integer availableStock;

    private String productName;

    private Integer productId;

    private Date createdAt;

    private Date modifiedAt;

    private Integer createdBy;

    private Integer modifiedBy;

    private Boolean isActive;

    private StoreLocation storeLocation;
}

