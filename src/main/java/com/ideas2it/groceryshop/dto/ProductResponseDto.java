package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private int id;

    private String name;

    private float price;

    private Date createdAt;

    private Date modifiedAt;

    private int createdBy;

    private int modifiedBy;

    private boolean isActive;
}
