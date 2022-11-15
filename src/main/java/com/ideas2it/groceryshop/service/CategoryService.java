package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SubCategoryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;

import java.util.List;

/**
 * @author RUBAN  03/11/2022
 * @version  1.0
 */
public interface CategoryService {
    SuccessResponseDto addCategory(CategoryRequestDto categoryRequestDto) throws Existed;

    List<CategoryResponseDto> getCategory() throws NotFound;

    List<SubCategoryResponseDto> getAllSubCategory() throws NotFound;

    SuccessResponseDto deleteCategory(Integer id) throws NotFound;

    SuccessResponseDto deleteSubCategory(Integer id, Integer subCategoryId) throws NotFound;

    SuccessResponseDto updateCategory(Integer id, CategoryRequestDto categoryRequestDto) throws Existed, NotFound;

    SuccessResponseDto updateSubCategory(Integer categoryId, Integer subCategoryId, CategoryRequestDto categoryRequestDto) throws NotFound, Existed;
}
