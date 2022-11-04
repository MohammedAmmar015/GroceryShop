package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.model.Category;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    public static Category toCategory(CategoryRequestDto categoryRequestDto) {
        Category category = new Category();
       // category.setId(categoryRequestDto.getParentId());
        category.setName(categoryRequestDto.getName());
        return category;
    }


}
