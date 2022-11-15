package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *     Cart-Details Request DTO, to hold Request Details
 * </p>
 * @author Mohammed Ammar
 * @since 03-11-2022
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDetailsRequestDto {

    @NotNull(message = "Product id cannot not be empty")
    private Integer productId;

    @NotNull(message = "Quantity cannot not be empty")
    private Integer quantity;
}
