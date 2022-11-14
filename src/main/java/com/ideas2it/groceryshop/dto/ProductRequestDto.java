package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     Product Request Dto class.
 * </p>
 * @author RUBAN 03/11/2022
 * @version  1.0
 */
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

    private String image;
}
