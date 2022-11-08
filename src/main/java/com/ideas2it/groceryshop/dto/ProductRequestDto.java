package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private int categoryId;

    private String name;

    private float price;

    private String unit;

    private int subCategoryId;

    private int createdBy;

    private int modifiedBy;

}
