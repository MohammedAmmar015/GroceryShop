package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *     Cart response DTO
 * </p>
 * @author Mohammed Ammar
 * @since 03-11-2022
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartResponseDto {
    private Integer id;

    private Float totalPrice;

    private List<CartDetailsResponseDto> cartDetails;

    private Date createdAt;
}
