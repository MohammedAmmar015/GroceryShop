package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 *     Stock Response DTO, to response stock details
 * </p>
 * @author Mohammed Ammar
 * @since 03-11-2022
 * @version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockResponseDto {

    private Integer id;

    private Integer availableStock;

    private Integer productId;

    private String productName;

    private String subCategory;

    private String category;

    private String area;

    private Integer pinCode;
}

