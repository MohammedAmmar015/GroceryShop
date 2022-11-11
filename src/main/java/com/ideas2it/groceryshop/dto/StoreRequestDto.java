package com.ideas2it.groceryshop.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class StoreRequestDto {

    @NotNull(message = "Pincode cannot be empty")
    private Integer pinCode;

    @NotEmpty(message = "Area cannot be empty")
    private String area;
}
