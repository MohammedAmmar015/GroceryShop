package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     This class is Category Response Dto and contains filtered fields.
 * </p>
 * @author RUBAN 03/11/2022
 * @version  1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {

    private int id;

    private String name;
}
