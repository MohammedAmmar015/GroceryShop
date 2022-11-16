/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SubCategoryResponseDto;
import com.ideas2it.groceryshop.model.Category;

/**
 * <p>
 *     This class contains method to convert dto object to model object
 *      and model object to dto object.
 * </p>
 *
 * @author RUBAN
 * @version 1.0
 * @since 03/11/22
 */
public class CategoryMapper {

    /**
     * <p>
     *     This method will convert Dto object to model object.
     * </p>
     * @param categoryRequestDto Dto type object.
     * @return category model.
     */
    public static Category toCategory(CategoryRequestDto categoryRequestDto) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        return category;
    }

    /**
     * <p>
     *     This method will convert model object to dto object.
     * </p>
     * @param category model type object
     * @return CategoryResponseDto object.
     */
    public static CategoryResponseDto toCategoryDto(Category category) {
        CategoryResponseDto categoryResponseDto1 = new CategoryResponseDto();
        categoryResponseDto1.setId(category.getId());
        categoryResponseDto1.setName(category.getName());
        return categoryResponseDto1;
    }

    /**
     * <p>
     *     This method will convert model object to dto object.
     * </p>
     * @param category model type object
     * @return SubCategoryResponseDto object.
     */
    public static SubCategoryResponseDto toSubCategoryDto(Category category) {
        SubCategoryResponseDto subCategoryResponseDto1 = new SubCategoryResponseDto();
        subCategoryResponseDto1.setId(category.getId());
        subCategoryResponseDto1.setName(category.getName());
        subCategoryResponseDto1.setCategoryName(category.getCategory().getName());
        return subCategoryResponseDto1;
    }
}
