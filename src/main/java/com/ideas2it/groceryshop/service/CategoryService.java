package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;

import java.util.List;

/**
 * @author RUBAN  03/11/2022
 * @version  1.0
 */
public interface CategoryService {
    SuccessDto addCategory(CategoryRequestDto categoryDto) throws ExistedException;

    List<CategoryResponseDto> getCategory() throws NotFoundException;

    List<CategoryResponseDto> getAllSubCategory() throws NotFoundException;

    SuccessDto deleteCategory(Integer id) throws NotFoundException;

    SuccessDto deleteSubCategory(Integer id, Integer subCategoryId) throws NotFoundException;

    SuccessDto updateCategory(Integer id, CategoryRequestDto categoryRequestDto) throws ExistedException, NotFoundException;

    SuccessDto updateSubCategory(Integer categoryId, Integer subCategoryId, CategoryRequestDto categoryRequestDto) throws NotFoundException, ExistedException;
}
