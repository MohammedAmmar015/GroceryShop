package com.ideas2it.groceryshop.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     Store Location Request DTO
 * </p>
 * @author Mohammed Ammar
 * @since 03-11-2022
 * @version 1.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoreLocationRequest {

    private Integer pinCode;

    private String area;
}
