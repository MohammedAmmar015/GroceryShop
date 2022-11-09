package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.model.Category;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     This class contains method to convert dto object to model object
 *      and model object to dto object.
 * </p>
 *
 * @author RUBAN 03/11/2022
 * @version 1.0
 */
@Component
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
        category.setCreatedBy(categoryRequestDto.getCreatedBy());
        category.setModifiedBy(categoryRequestDto.getCreatedBy());
        return category;
    }

    /**
     * <p>
     *     This method will convert model object to dto object.
     * </p>
     * @param category model type object
     * @return Dto object.
     */
    public static CategoryResponseDto toCategoryDto(Category category) {
        CategoryResponseDto categoryResponseDto1 = new CategoryResponseDto();
        categoryResponseDto1.setId(category.getId());
        categoryResponseDto1.setName(category.getName());
        return categoryResponseDto1;
    }
}
