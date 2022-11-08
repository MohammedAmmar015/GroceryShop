package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFoundException;

import java.util.List;

public interface CategoryService {
    String addCategory(CategoryRequestDto categoryDto) throws Existed;

    List<CategoryResponseDto> getCategory() throws NotFoundException;

    List<CategoryResponseDto> getAllSubCategory() throws NotFoundException;

    String deleteCategory(Integer id) throws NotFoundException;

    String deleteSubCategory(Integer id, Integer subCategoryId) throws NotFoundException;

    String updateCategory(Integer id, CategoryRequestDto categoryRequestDto) throws Existed, NotFoundException;

    String updateSubCategory(Integer categoryId, Integer subCategoryId, CategoryRequestDto categoryRequestDto) throws NotFoundException, Existed;
}
