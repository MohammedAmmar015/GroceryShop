package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public String addCategory(CategoryRequestDto categoryDto);

   // Optional<Category> getCategoryById(Integer id);

    public List<CategoryResponseDto> getCategory();

    List<CategoryResponseDto> getAllSubCategory();

}
