/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SubCategoryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.CategoryService;

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
import java.util.List;

/**
 * <p>
 *     It implements method of CRUD operations for Category.
 * </p>
 * @author RUBAN
 * @version  1.0
 * @since 03/11/22
 *
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
     *     This Method will receives category request dto and
     *     forwards it to the service layer for adding category,
     *     it handles incoming APIs (api/v1/categories).
     * </p>
     * @param categoryRequestDto It receives DTO type object from users.
     * @return return Success response dto ,if exception occurs it will
     *          return Error response dto
     */
    @PostMapping

    public SuccessResponseDto addCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto)
            throws Existed, NotFound {
        logger.debug("Entered into addCategory method in category controller");
        return categoryService.addCategory(categoryRequestDto);
    }

    /**
     * <p>
     *     This method used to get all category from the
     *     data base and return in category response dto,
     *     it handles get method API of (api/v1/categories).
     * </p>
     *
     * @return CategoryResponseDto object if method pass otherwise,
     *         Error response dto will be thrown.
     * @throws NotFound exception will be thrown if category is empty.
     */
    @GetMapping
    public List<CategoryResponseDto> getCategory() throws NotFound {
        logger.debug("Entered into getCategory method in category controller");
        return categoryService.getCategory();
    }

    /**
     * <p>
     *     This method will get all the sub category in data base,
     *     handles Get method APIs(api/v1/categories/subCategories).
     * </p>
     * @return subCategoryResponse dto.
     * @throws NotFound exception will be thrown if sub category is empty.
     */
    @GetMapping("/subCategories")
    public List<SubCategoryResponseDto> getSubCategory() throws NotFound {
        logger.debug("Entered into getSubcategory method of category controller");
        return categoryService.getAllSubCategory();
    }

    /**
     * <p>
     *     This method will delete(soft delete) category from the data
     *     base using id, handles delete API(api/v1/categories/{id}).
     * </p>
     *
     * @param id to delete category.
     * @return SuccessDto.
     * @throws NotFound exception will be thrown when the id not exist.
     */
    @DeleteMapping("/{id}")
    public SuccessResponseDto deleteCategory(@PathVariable("id") Integer id) throws NotFound {
        logger.debug("Entered into deleteCategory method of category controller");
        return categoryService.deleteCategory(id);

    }

    /**
     * <p>
     *    This method will delete(soft delete) category using categoryId & subCategoryId,
     *     handles the Delete API ("api/v1/categories/subCategories/{categoryId}/{subCategoryId}).
     * </p>
     *
     * @param parentId to delete Sub category.
     * @param categoryId to delete sub category.
     * @return SuccessResponseDto
     * @throws NotFound exception will be thrown if the id not exist.
     */
    @DeleteMapping("/subCategories/{parentId}/{categoryId}")
    public SuccessResponseDto deleteSubCategory(@PathVariable("parentId") Integer parentId,
                                                @PathVariable("categoryId") Integer categoryId)
            throws NotFound {
        logger.debug("Entered into deleteSubCategory method in category controller");
        return categoryService.deleteSubCategory(parentId, categoryId);
    }

    /**
     * <p>
     *     This method used to update category Details in
     *     data base, handles Put method API (api/v1/categories/{id}).
     * </p>
     * @param id to find which object to update
     * @param categoryRequestDto values to be update.
     * @return SuccessResponseDto
     * @throws Existed exception will be thrown if new values are same as old.
     * @throws NotFound exception will be thrown if the id not exist.
     */
    @PutMapping("/{id}")
    public SuccessResponseDto updateCategory(@PathVariable("id") Integer id,
                                             @RequestBody CategoryRequestDto categoryRequestDto)
            throws Existed, NotFound {
        logger.debug("Entered into updateCategory method in category controller");
        return categoryService.updateCategory(id, categoryRequestDto);
    }

    /**
     * <p>
     *     This method to update sub category fields,
     *     handles Put method API (api/v1/categories/subCategories/{parentId}/{categoryId}).
     * </p>
     *
     * @param categoryId to update.
     * @param parentId to fetch correct sub category.
     * @param categoryRequestDto values to be Update.
     * @return SuccessDto
     * @throws Existed exception will be thrown if new values are same as old.
     * @throws NotFound exception will be thrown if the id is not exist.
     */
    @PutMapping("/subCategories/{parentId}/{categoryId}")
    public SuccessResponseDto updateSubCategory(@PathVariable("parentId") Integer parentId,
                                                @PathVariable("categoryId") Integer categoryId,
                                                @RequestBody CategoryRequestDto categoryRequestDto)
            throws Existed, NotFound {
        logger.debug("Entered into updateSubCategory method in category controller");
        return categoryService.updateSubCategory(parentId, categoryId, categoryRequestDto);
    }
}
