package com.ideas2it.groceryshop.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {

    private String name;

    private Integer parentId;

   // private List<ProductRequestDto> products;

}

