/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.ideas2it.groceryshop.model.Category;

/**
 * <p>
 *     Provides create, update, view and delete method for category.
 * </p>
 *
 * @author  RUBAN
 * @version  1.0
 * @since 05/11/22
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * <p>
     *     To fetch all active sub category.
     * </p>
     *
     * @param status - To get active or inactive sub category.
     * @return - list of sub category.
     */
    @Query(value = "select * from category where parent_id is not null and is_active = ?1",
            nativeQuery = true)
     List<Category> findByParentIdNotNullAndIsActive(Boolean status);

    /**
     * <p>
     *     To fetch particular category by id and parent id is null
     * </p>
     *
     * @param id - To fetch relevant category.
     * @param status - To fetch active or inactive category
     * @return - category details.
     */
    @Query(value = "select * from category where id = ?1 and is_active = ?2 and parent_id is null",
            nativeQuery = true)
    Category findByIdAndParentIdAndIsActive(Integer id, Boolean status);

    /**
     * <p>
     *     Retrieve particular category to update.
     * </p>
     * @param id - To fetch relevant object
     * @param status - To get active category
     * @return - category object
     */
    Category findByIdAndIsActive(Integer id ,Boolean status);

    /**
     * <p>
     *     Retrieve list of sub category by parent id.
     * </p>
     *
     * @param id - To fetch relevant sub categories
     * @return - list of sub category
     */
    @Query(value ="select * from category where parent_id = ?1", nativeQuery = true)
    List<Category>  findSubCategoryByParentId(Integer id);

    /**
     * <p>
     *     Retrieve list of active or inactive category.
     * </p>
     *
     * @param status - To fetch active or inactive categories
     * @return - list of category
     */
    @Query(value ="select * from category where parent_id is null and is_active = ?1",
            nativeQuery = true)
    List<Category> findByParentIdAndIsActive( Boolean status);

    /**
     * <p>
     *     Retrieve active or inactive sub category by parentId and categoryId
     * </p>
     *
     * @param parentId - To ensure it is sub category
     * @param categoryId - To get sub category.
     * @param status - to get active or inactive sub category
     * @return - sub category details
     */
    @Query(value = "select * from category where parent_id = ?1 and id = ?2 and is_active = ?3",
            nativeQuery = true)
    Category findSubCategoryByParentIdAndCategoryIdAndIsActive(Integer parentId, Integer categoryId,
                                                               Boolean status);

    /**
     * <p>
     *     To check whether category name already exist or not.
     * </p>
     *
     * @param name - To check name exist or not
     * @return - If name exist return true or-else false
     */
    Boolean existsByName(String name);

    /**
     * <p>
     *     To check sub category id exists.
     * </p>
     *
     * @param subCategoryId - To check sub category.
     * @return - If id exists return true or-else false
     */
    Boolean existsById(int subCategoryId);
}



