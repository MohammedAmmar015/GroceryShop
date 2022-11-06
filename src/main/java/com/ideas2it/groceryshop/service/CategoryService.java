package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    String addCategory(CategoryRequestDto categoryDto);

    List<CategoryResponseDto> getCategory();

    List<CategoryResponseDto> getAllSubCategory();

    String deleteCategory(Integer id);

    String deleteSubCategory(Integer id, Integer subCategoryId);

    String updateCategory(Integer id, String categoryName);
}
