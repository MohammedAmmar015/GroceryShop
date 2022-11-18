/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     It is used to show order details response dto of an order which contains categoryName,
 *     subCategoryName, productName, quantity, price
 * </p>
 *
 * @author Dhanalakshmi M
 * @version 1.0
 * @since 18-11-2022
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
