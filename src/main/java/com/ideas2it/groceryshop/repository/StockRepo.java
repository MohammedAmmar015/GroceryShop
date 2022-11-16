/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface StockRepo extends JpaRepository<Stock, Integer> {

    /**
     * <p>
     *     This method is used to retrieve Stock by Product Id
     * </p>
     * @param productId - product's id to retrieve stock
     * @return list of stock
     */
    List<Stock> findByProductId(Integer productId);

    /**
     * <p>
     *     This method is used to retrieve stock by product and location
     * </p>
     * @param productId - product id
     * @param locationId - location id(store location)
     * @return Stock details for given product id and location id
     */
    Stock findByProductIdAndStoreLocationId(Integer productId, Integer locationId);

    /**
     * <p>
     *     This method is used to update stock by product and location
     * </p>
     * @param stock - stock to update
     * @param productId - to which product
     * @param locationId - in which location
     * @return number of rows affected
     */
    @Query("UPDATE Stock s SET s.availableStock = s.availableStock + ?1 where s.product.id = ?2 AND s.storeLocation.id = ?3")
    @Modifying
    Integer updateStockByProductAndLocation(Integer stock, Integer productId, Integer locationId);

    /**
     * <p>
     *     This method is used to decrease stock by order details
     * </p>
     * @param quantity - quantity to decrease
     * @param product - to which product
     * @param locationId - in which location
     */
    @Query("UPDATE Stock s SET s.availableStock = s.availableStock - ?1 where s.product = ?2 AND s.storeLocation.id = ?3")
    @Modifying
    @Transactional
    void decreaseStockByProductsAndLocation(Integer quantity, Product product, Integer locationId);

    /**
     * <p>
     *     This method is used to check if stock is exist or not
     *     for particular product on particular location
     * </p>
     * @param locationId - in which location
     * @param productId - to which product
     * @return true if exists else false
     */
    Boolean existsByStoreLocationIdAndProductId(Integer locationId, Integer productId);

    /**
     * <p>
     *     This method is used to check if Stock is Greater than given number or not
     *     based on product and location
     * </p>
     * @param locationId - in which location
     * @param productId - for which product
     * @param expectedNumber - minimum stock to check
     * @return true if stock is greater that expected number
     */
    Boolean existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThan(Integer locationId, Integer productId, Integer expectedNumber);
}
