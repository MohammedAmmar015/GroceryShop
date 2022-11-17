package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     It is an order details response dto which is used for showing output to the user
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsResponseDto {

    private String categoryName;

    private String subCategoryName;

    private String productName;

    private Integer quantity;

    private Float price;

}
