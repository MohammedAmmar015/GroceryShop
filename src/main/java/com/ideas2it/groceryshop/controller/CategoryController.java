/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SubCategoryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.CategoryService;

import java.util.List;

/**
 * <p>
 *     Provides create, view, update and delete operations for Category.
 * </p>
 *
 * @author RUBAN
 * @version  1.0
 * @since 03/11/22
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final Logger logger;
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
        this.logger = LogManager.getLogger(CategoryController.class);
    }

    /**
     * <p>
     *     To create category by using category request dto.
     * </p>
     *
     * @param categoryRequestDto - Contains name, parentId.
     * @return SuccessResponseDto - Contains success message and status code.
     * @throws ExistedException - If category already exists.
     * @throws NotFoundException - If category id not found for adding sub categories.
     */
    @PostMapping
    public SuccessResponseDto addCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto)
                                          throws ExistedException, NotFoundException {
        logger.debug("Entered into addCategory method in category controller");
        return categoryService.addCategory(categoryRequestDto);
    }

    /**
     * <p>
     *     To get available categories.
     * </p>
     *
     * @return - List of category.
     * @throws NotFoundException - If category not found.
     */
    @GetMapping
    public List<CategoryResponseDto> getCategory() throws NotFoundException {
        logger.debug("Entered into getCategory method in category controller");
        return categoryService.getCategory();
    }

    /**
     * <p>
     *     To get all available sub categories.
     * </p>
     *
     * @return - List of sub category.
     * @throws NotFoundException - If sub category not found.
     */
    @GetMapping("/subCategories")
    public List<SubCategoryResponseDto> getSubCategory() throws NotFoundException {
        logger.debug("Entered into getSubcategory method of category controller");
        return categoryService.getAllSubCategory();
    }

    /**
     * <p>
     *     Delete category by using category id.
     * </p>
     *
     * @param id - To fetch relevant category.
     * @return - Success response dto with message and status code.
     * @throws NotFoundException - If category not found.
     */
    @DeleteMapping("/{id}")
    public SuccessResponseDto deleteCategory(@PathVariable("id") Integer id) throws NotFoundException {
        logger.debug("Entered into deleteCategory method of category controller");
        return categoryService.deleteCategory(id);
    }

    /**
     * <p>
     *     Delete sub category by using sub category id.
     * </p>
     *
     * @param parentId - To find relevant sub category.
     * @param categoryId - To delete sub category.
     * @return - Success response dto with message and status code.
     * @throws NotFoundException - If sub category not found.
     */
    @DeleteMapping("/{parentId}/subCategories/{categoryId}")
    public SuccessResponseDto deleteSubCategory(@PathVariable("parentId") Integer parentId,
                                                @PathVariable("categoryId") Integer categoryId)
                                                throws NotFoundException {
        logger.debug("Entered into deleteSubCategory method in category controller");
        return categoryService.deleteSubCategory(parentId, categoryId);
    }

    /**
     * <p>
     *     Update category fields by using category request dto and category id.
     * </p>
     *
     * @param id - To find which object to update
     * @param categoryRequestDto - Contains name to get update.
     * @return - Success response dto with message and status code.
     * @throws ExistedException - If category fields already exists.
     * @throws NotFoundException - If category not found.
     */
    @PutMapping("/{id}")
    public SuccessResponseDto updateCategory(@PathVariable("id") Integer id,
                                             @RequestBody CategoryRequestDto categoryRequestDto)
                                             throws ExistedException, NotFoundException {
        logger.debug("Entered into updateCategory method in category controller");
        return categoryService.updateCategory(id, categoryRequestDto);
    }

    /**
     * <p>
     *     Update sub category fields by using sub category request dto and sub category id.
     * </p>
     *
     * @param categoryId -To find relevant sub category.
     * @param parentId - To fetch sub category.
     * @param categoryRequestDto - Contains category name, parentId to be updated.
     * @return - Success response dto with message and status code.
     * @throws ExistedException - If sub category fields already exists.
     * @throws NotFoundException - If sub category not found.
     */
    @PutMapping("/{parentId}/subCategories/{categoryId}")
    public SuccessResponseDto updateSubCategory(@PathVariable("parentId") Integer parentId,
                                                @PathVariable("categoryId") Integer categoryId,
                                                @RequestBody CategoryRequestDto categoryRequestDto)
                                                throws ExistedException, NotFoundException {
        logger.debug("Entered into updateSubCategory method in category controller");
        return categoryService.updateSubCategory(parentId, categoryId, categoryRequestDto);
    }
}
