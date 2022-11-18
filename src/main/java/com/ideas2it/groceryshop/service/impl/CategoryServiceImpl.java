/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SubCategoryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.mapper.CategoryMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.repository.CategoryRepository;
import com.ideas2it.groceryshop.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *     This service class is implemented to perform category related CRUD operations.
 * </p>
 *
 * @author RUBAN
 * @version  1.0
 * @since 03/11/22
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final Logger logger;
    private final ProductHelper productHelper;

    public CategoryServiceImpl(ProductHelper productHelper,CategoryRepository categoryRepository) {
        this.productHelper = productHelper;
        this.categoryRepository = categoryRepository;
        this.logger = LogManager.getLogger(CategoryServiceImpl.class);
    }

    /**
     * <p>
     *     This method to add category to data base,
     *     before that will validate name, id and finally
     *     will allow to add in data base.
     * </p>
     * @param categoryRequestDto dto type object
     * @return SuccessDto
     * @throws ExistedException will be thrown if category already Exists.
     */
    @Override
    public SuccessResponseDto addCategory(CategoryRequestDto categoryRequestDto)
            throws ExistedException, NotFoundException {
        logger.debug("Entered into addCategory method in category service");
        if(categoryRepository.existsByName(categoryRequestDto.getName())) {
            throw new ExistedException("Category already added");
        }
        Category categories = CategoryMapper.toCategory(categoryRequestDto);
        if(categoryRequestDto.getParentId() != 0) {
            Optional<Category> category = categoryRepository.findById(categoryRequestDto.
                    getParentId());
            if(category.isPresent()) {
                categories.setCategory(category.get());
            } else  {
                throw new NotFoundException("Category not found");
            }
        }
        categoryRepository.save(categories);
        logger.debug("addCategory method successfully executed");
        if (categoryRequestDto.getParentId() == 0) {
            return new SuccessResponseDto(201, "Category added successfully");
        }
        return new SuccessResponseDto(201, "Sub category added successfully");
    }

    /**
     * <p>
     *     This method to retrieve all category list from
     *     the data base and convert it into dto type object
     *     in category mapper and then return it to controller.
     * </p>
     * @return Category List.
     * @throws NotFoundException will be thrown if no category is added.
     */
    @Override
    public List<CategoryResponseDto> getCategory() throws NotFoundException {
        logger.debug("Entered into getCategory method in category service");
        List<Category> resultCategories = categoryRepository.findByParentIdAndIsActive( true);
        if (resultCategories.isEmpty()) {
            throw new NotFoundException("Category not added");
        }
        List<CategoryResponseDto> categoriesList = new ArrayList<>();
        for(Category category : resultCategories) {
            categoriesList.add(CategoryMapper.toCategoryDto(category));
        }
        logger.debug("getCategory method successfully executed");
        return categoriesList;
    }

    /**
     * <p>
     *     This method will get all sub categories which are in exist
     *     from the data base and convert into dto type object with
     *     the help of category mapper and then will return it to controller
     * </p>
     * @return sub category list if exist otherwise exception will be thrown.
     * @throws NotFoundException will be thrown if no sub category exists.
     */
    @Override
    public List<SubCategoryResponseDto> getAllSubCategory() throws NotFoundException {
        logger.debug("Entered into getAllSubCategory method in category service");
        List<Category> categories = categoryRepository.findByParentIdNotNullAndIsActive(true);
        if (categories.isEmpty()) {
            throw new NotFoundException("Sub category not added");
        }
        List<SubCategoryResponseDto> categoriesList = new ArrayList<>();
        for(Category category : categories) {
            categoriesList.add(CategoryMapper.toSubCategoryDto(category));
        }
        logger.debug("getAllSubCategory method executed successfully");
        return categoriesList;
    }

    /**
     * <p>
     *     This method will delete(soft delete) category from the data base
     *     and return success response dto to controller.
     * </p>
     * @param id to find which object to get deleted.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFoundException will be thrown if category doesn't exist.
     */
    @Override
    public SuccessResponseDto deleteCategory(Integer id) throws NotFoundException {
        logger.debug("Entered into deleteCategory method in category service");
        Category categories = categoryRepository.findByIdAndIsActive(id, true);
        if (categories == null) {
            throw new NotFoundException("Category id not found");
        }
        categories.setActive(false);
        List<Category> subCategories = categoryRepository.findSubCategoryByParentId(id);
        for (Category category : subCategories) {
            category.setActive(false);
            categoryRepository.save(category);
        }
        productHelper.getProductByCategoryIdAndSetFalse(id);
        categoryRepository.save(categories);
        logger.debug("deleteCategory method successfully executed");
        return new SuccessResponseDto(200, "category deleted Successfully");
    }

    /**
     * <p>
     *     This method will delete(soft delete) sub category from the data base
     *      and return success response dto to controller.
     * </p>
     * @param parentId to find which object to get deleted.
     * @param categoryId to find which object to get deleted.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFoundException will be thrown if sub category doesn't exist.
     */
    @Override
    public SuccessResponseDto deleteSubCategory(Integer parentId, Integer categoryId)
            throws NotFoundException {
        logger.debug("Entered into deleteSubCategory method in category service");
        Category category = categoryRepository.
                findSubCategoryByParentIdAndCategoryIdAndIsActive(parentId, categoryId, true);
        if (category == null) {
            throw new NotFoundException("Sub category id not found");
        }
        category.setActive(false);
        categoryRepository.save(category);
        productHelper.getProductBySubCategoryIdAndSetFalse(categoryId);
        logger.debug("deleteSubCategory method successfully executed");
        return new SuccessResponseDto(200, "Subcategory deleted successfully");
    }

    /**
     * <p>
     *     This method used to update particular category using
     *     its id and return success response dto which includes success message.
     * </p>
     * @param id to find which object to update.
     * @param categoryRequestDto contains values to get updated.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFoundException will be thrown if id doesn't match.
     * @throws ExistedException will be thrown if values are already exist.
     */
    @Override
    public SuccessResponseDto updateCategory(Integer id, CategoryRequestDto categoryRequestDto)
            throws NotFoundException, ExistedException {
        logger.debug("Entered into updateCategory method in category service");
        Category category = categoryRepository.findByIdAndParentIdAndIsActive(id, true);
        if(category == null) {
            throw new NotFoundException("Category id not found");
        }
        if (categoryRepository.existsByName(categoryRequestDto.getName())) {
            throw new ExistedException("Category name already exist");
        }
        category.setName(categoryRequestDto.getName());
        categoryRepository.save(category);
        logger.debug("updateCategory method successfully executed");
        return new SuccessResponseDto(200, "Category details updated successfully");
    }

    /**
     * <p>
     *     This method used to update particular sub category using
     *     its id and return success response dto which includes success message.
     * </p>
     * @param categoryId to find object to get update.
     * @param parentId to find object to get update.
     * @param categoryRequestDto contains values to get updated.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFoundException will be thrown if id doesn't match.
     * @throws ExistedException will be thrown if values are already exist.
     */
    @Override
    public SuccessResponseDto updateSubCategory(Integer parentId, Integer categoryId,
                                                CategoryRequestDto categoryRequestDto)
            throws NotFoundException, ExistedException {
        logger.debug("Entered into updateSubCategory method in category service");
        Category category = categoryRepository.
                findSubCategoryByParentIdAndCategoryIdAndIsActive(parentId, categoryId, true);
        if(category == null) {
            throw new NotFoundException("Subcategory id not found");
        }
        if (categoryRepository.existsByName(categoryRequestDto.getName())) {
            throw new ExistedException("Subcategory name already exist");
        }
        category.setName(categoryRequestDto.getName());
        categoryRepository.save(category);
        logger.debug("updateSubCategory method successfully executed");
        return new SuccessResponseDto(200, "Subcategory updated successfully");
    }

    /**
     * <p>
     *     This method used to find category object from database if the
     *     id is exist, this method useful for add product method in product service.
     * </p>
     * @param categoryId to find correct object in database
     * @return category object.
     */
    public Optional<Category> findCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId);
    }

    /**
     * <p>
     *     This method used check whether sub category id is exist in database or
     *     not and returns true if exist otherwise false, this method is useful for
     *     add product method in product service.
     * </p>
     * @param subCategoryId to check this id exist or not in database.
     * @return true if exist otherwise false.
     */
    public Boolean existBySubCategoryId(Integer subCategoryId) {
        return categoryRepository.existsById(subCategoryId);
    }
}
