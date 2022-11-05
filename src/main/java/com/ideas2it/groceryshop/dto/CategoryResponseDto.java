package com.ideas2it.groceryshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ideas2it.groceryshop.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {

    private int id;

    private String name;

    //private List<Category> category;

    //private Date createdAt;

   // private Date modifiedAt;

   // private int createdBy;

    //private int modifiedBy;

    //private boolean isActive;
}
