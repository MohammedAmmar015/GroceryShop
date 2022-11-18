/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *     This class implements method for crud operations of category
 *     by interacting with database,it has predefined method.
 * </p>
 * @author  RUBAN
 * @version  1.0
 * @since 05/11/22
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * <p>
     *     This method to fetch all sub category object from
     *     data base based on id
     * </p>
     * @param status to ensure only active sub category.
     * @return list of sub categories.
     */
    @Query(value = "select * from category  where parent_id is not null and is_active = ?1",
            nativeQuery = true)
     List<Category> findByParentIdNotNullAndIsActive(Boolean status);

    /**
     * <p>
     *     This method is to fetch particular category by id
     *     and parent id is null
     * </p>
     * @param id to fetch correct object
     * @param status to fetch only active category
     * @return category object
     */
    @Query(value = "select * from category where id = ?1 and is_active = ?2 and parent_id is null",
            nativeQuery = true)
    Category findByIdAndParentIdAndIsActive(Integer id, Boolean status);

    /**
     * <p>
     *     This method retrieve particular category object to update it.
     * </p>
     * @param id to fetch correct object
     * @param status to get active category
     * @return category object
     */
    Category findByIdAndIsActive(Integer id ,Boolean status);

    /**
     * <p>
     *     This method retrieve particular sub category object by id
     * </p>
     * @param id to fetch correct object
     * @return sub category object
     */
    @Query(value ="select * from category where parent_id = ?1", nativeQuery = true)
    List<Category>  findSubCategoryByParentId(Integer id);

    /**
     * <p>
     *     This method will get all category from data base.
     * </p>
     * @param status to fetch only active object
     * @return list of category
     */
    @Query(value ="select * from category where parent_id is null and is_active = ?1",
            nativeQuery = true)
    List<Category> findByParentIdAndIsActive( Boolean status);

    /**
     * <p>
     *     This method will get sub category object from data base
     *     by id and then to delete it.
     * </p>
     * @param parentId to ensure it is sub category
     * @param categoryId id of sub category
     * @param status to get only active sub categories
     * @return category object
     */
    @Query(value = "select * from category where parent_id = ?1 and id = ?2 and is_active = ?3",
            nativeQuery = true)
    Category findSubCategoryByParentIdAndCategoryIdAndIsActive(Integer parentId, Integer categoryId,
                                                               Boolean status);

    /**
     * <p>
     *     This method to check whether name already exist or not in
     *     date base.
     * </p>
     * @param name to check name exist or not
     * @return true will be return if name exist, otherwise false
     */
    Boolean existsByName(String name);
}



