package com.ideas2it.groceryshop.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 *     Cart Request DTO
 * </p>
 * @author Mohammed Ammar
 * @since 03-11-2022
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartRequestDto {

    @NotEmpty(message = "Cart details cannot be empty")
    private CartDetailsRequestDto cartDetails;
}
