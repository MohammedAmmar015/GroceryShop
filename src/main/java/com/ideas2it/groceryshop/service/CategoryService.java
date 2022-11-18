/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SubCategoryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.model.Category;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *     This class is an interface holds methods to be implemented in category service layer
 *     which implements this interface.
 * </p>
 * @author RUBAN
 * @version  1.0
 * @since 03/11/22
 */
public interface CategoryService {

    /**
     * <p>
     *     This method to add category to data base,
     *     before that will validate name, id and finally
     *     will allow to add in data base.
     * </p>
     * @param categoryRequestDto dto type object.
     * @return SuccessDto
     * @throws ExistedException will be thrown if category already Exists.
     */
    SuccessResponseDto addCategory(CategoryRequestDto categoryRequestDto)
            throws ExistedException, NotFoundException;

    /**
     * <p>
     *     This method to retrieve all category list from
     *     the data base and convert it into dto type object
     *     in category mapper and then return it to controller.
     * </p>
     * @return Category List.
     * @throws NotFoundException will be thrown if no category is added.
     */
    List<CategoryResponseDto> getCategory() throws NotFoundException;

    /**
     * <p>
     *     This method will get all sub categories which are in exist
     *     from the data base and convert into dto type object with
     *     the help of category mapper and then will return it to controller
     * </p>
     * @return sub category list if exist otherwise exception will be thrown.
     * @throws NotFoundException will be thrown if no sub category exists.
     */
    List<SubCategoryResponseDto> getAllSubCategory() throws NotFoundException;

    /**
     * <p>
     *     This method will delete(soft delete) category from the data base
     *     and return success response dto to controller.
     * </p>
     * @param id to find which object to get deleted.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFoundException will be thrown if category doesn't exist.
     */
    SuccessResponseDto deleteCategory(Integer id) throws NotFoundException;

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
    SuccessResponseDto deleteSubCategory(Integer parentId, Integer categoryId) throws NotFoundException;

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
    SuccessResponseDto updateCategory(Integer id, CategoryRequestDto categoryRequestDto)
            throws ExistedException, NotFoundException;

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
    SuccessResponseDto updateSubCategory(Integer parentId, Integer categoryId, CategoryRequestDto
            categoryRequestDto) throws NotFoundException, ExistedException;

    /**
     * <p>
     *     This method used to find category object from database if the
     *     id is exist, this method useful for method in category service.
     * </p>
     * @param categoryId to find correct object in database
     * @return category object.
     */
    Optional<Category> findCategoryById(Integer categoryId);

    /**
     * <p>
     *     This method used check whether sub category id is exist in database or
     *     not and returns true if exist otherwise false.
     * </p>
     * @param subCategoryId to check this id exist or not in database.
     * @return true if exist otherwise false.
     */
    Boolean existBySubCategoryId(Integer subCategoryId);
}
