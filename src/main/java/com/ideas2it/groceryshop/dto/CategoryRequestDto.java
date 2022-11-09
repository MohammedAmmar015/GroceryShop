package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *     Category Request Dto class.
 * </p>
 * @author RUBAN 03/11/2022
 * @version  1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {

    private String name;

    private Integer parentId;

    private int createdBy;

}

