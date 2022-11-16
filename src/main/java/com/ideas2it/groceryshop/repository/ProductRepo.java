/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *     This class implements method for crud operations of product
 *  *     by interacting with database.
 * </p>
 * @author  RUBAN
 * @version  1.0
 * @since 05/11/22
 */
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    /**
     * <p>
     *     This method to get particular product by id.
     * </p>
     * @param productId to find correct object
     * @param status to get only active product
     * @return product object
     */
    Product findByIdAndIsActive(Integer productId, Boolean status);

    /**
     * <p>
     *     This method will get all active products from data base.
     * </p>
     * @param status to get only active products
     * @return list of products
     */
    @Query(value = "select * from product where is_active = ?1", nativeQuery = true)
    List<Product> findAllAndIsActive(Boolean status);

    /**
     * <p>
     *     This method will get all products from particular category.
     * </p>
     * @param categoryId to get all products in that category
     * @param status to get only active products
     * @return list of products.
     */
    @Query(value ="select * from product p join category sc on sc.id = p.sub_category_id join" +
            " category c on c.id = sc.parent_id where c.id = ?1 and p.is_active = true", nativeQuery = true)
    List<Product> findProductsByCategoryIdAndIsActive(Integer categoryId, Boolean status);

    /**
     * <p>
     *     This method will get all products in sub category
     *     by id.
     * </p>
     * @param subCategoryId to get relevant products
     * @param status to get only active products
     * @return list fo products in sub category
     */
    @Query(value = "select * from product where sub_category_id = ?1 and is_active = ?2",
            nativeQuery = true)
    List<Product> findBySubCategoryIdAndIsActive(Integer subCategoryId, Boolean status);

    /**
     * <p>
     *     This method used to compare products in database whether all details are already exist,
     *     if it exist will return true.
     * </p>
     * @param name to check name
     * @param price to check price
     * @param unit to check unit
     * @return true or false
     */
    boolean existsByNameAndPriceAndUnit(String name, float price, String unit);

    /**
     * <p>
     *     This method helps while adding product, if product name is
     *     same then it will return true.
     * </p>
     * @param name to check name already exists
     * @return true or false
     */
    boolean existsByName(String name);
}


