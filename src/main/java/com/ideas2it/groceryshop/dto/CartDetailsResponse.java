package com.ideas2it.groceryshop.dto;

import com.ideas2it.groceryshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 *     Cart-Details Response DTO
 * </p>
 * @author Mohammed Ammar
 * @since 03-11-2022
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDetailsResponse {
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