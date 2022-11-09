package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     This class is Product Response Dto and contains filtered fields.
 * </p>
 * @author RUBAN 03/11/2022
 * @version  1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private int id;

    private String name;

    private float price;

    private String unit;
}
