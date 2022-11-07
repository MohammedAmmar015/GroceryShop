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
        category.setName(categoryRequestDto.getName());
        category.setCreatedBy(categoryRequestDto.getCreatedBy());
        category.setModifiedBy(categoryRequestDto.getCreatedBy());
        return category;
    }

    public static CategoryResponseDto toCategoryDto(Category category) {
        CategoryResponseDto categoryResponseDto1 = new CategoryResponseDto();
        categoryResponseDto1.setId(category.getId());
        categoryResponseDto1.setName(category.getName());
       // categoryResponseDto1.set
        return categoryResponseDto1;
    }


}
