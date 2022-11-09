package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;

import java.util.List;

/**
 * @author RUBAN  03/11/2022
 * @version  1.0
 */
public interface CategoryService {
    SuccessDto addCategory(CategoryRequestDto categoryDto) throws Existed;

    List<CategoryResponseDto> getCategory() throws NotFound;

    List<CategoryResponseDto> getAllSubCategory() throws NotFound;

    SuccessDto deleteCategory(Integer id) throws NotFound;

    SuccessDto deleteSubCategory(Integer id, Integer subCategoryId) throws NotFound;

    SuccessDto updateCategory(Integer id, CategoryRequestDto categoryRequestDto) throws Existed, NotFound;

    SuccessDto updateSubCategory(Integer categoryId, Integer subCategoryId, CategoryRequestDto categoryRequestDto) throws NotFound, Existed;
}
