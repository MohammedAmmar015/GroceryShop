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

/**
 * <p>
 * This is StockRepository which extends JpaRepository,
 * by default we can use default methods available in JpaRepository,
 * We can also add our custom methods in this repository to do
 * database operations on Stock Entity
 * </p>
 *
 * @author Mohammed Ammar
 * @version 1.0
 * @since 03-11-2022
 */
public interface StockRepository extends JpaRepository<Stock, Integer> {

    /**
     * <p>
     * This method is used to retrieve all Stocks of
     * particular product which available in multiple location
     * based on Product Id
     * </p>
     *
     * @param productId - product's id to retrieve stock
     * @return list of stock
     */
    List<Stock> findByProductId(Integer productId);

    /**
     * <p>
     * This method is used to retrieve stock details
     * of particular product available on particular location
     * based on location id and product id
     * </p>
     *
     * @param productId  - product id
     * @param locationId - location id(store location)
     * @return Stock details for given product id and location id
     */
    Stock findByProductIdAndStoreLocationId(Integer productId, Integer locationId);

    /**
     * <p>
     * This method is used to update stock of particular
     * product on any particular location based on
     * location id and product id
     * </p>
     *
     * @param stock      - stock to update
     * @param productId  - to which product
     * @param locationId - in which location
     * @return number of rows affected
     */
    @Query("UPDATE Stock s SET s.availableStock = s.availableStock + ?1 "
            + "where s.product.id = ?2 AND s.storeLocation.id = ?3")
    @Modifying
    @Transactional
    Integer updateStockByProductAndLocation(Integer stock,
                                            Integer productId,
                                            Integer locationId);

    /**
     * <p>
     * This method is used to decrease stock of
     * product that user has ordered based on
     * product id, quantity and location id
     * </p>
     *
     * @param quantity   - quantity to decrease
     * @param product    - to which product
     * @param locationId - in which location
     */
    @Query("UPDATE Stock s SET s.availableStock = s.availableStock - ?1 "
            + "where s.product = ?2 AND s.storeLocation.id = ?3")
    @Modifying
    @Transactional
    void decreaseStockByProductsAndLocation(Integer quantity,
                                            Product product,
                                            Integer locationId);

    /**
     * <p>
     * This method is used to check if stock is available or not
     * for particular product on particular location
     * based on product id and location id
     * </p>
     *
     * @param locationId - in which location
     * @param productId  - to which product
     * @return true if exists else false
     */
    Boolean existsByStoreLocationIdAndProductId(Integer locationId,
                                                Integer productId);

    /**
     * <p>
     * This method is used to check if Stock is greater than
     * expected number or not
     * based on product, location and expectedNumber
     * </p>
     *
     * @param locationId     - in which location
     * @param productId      - for which product
     * @param expectedNumber - minimum stock to check
     * @return true if stock is greater that expected number
     */
    Boolean existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThan(Integer locationId,
                                                                            Integer productId,
                                                                            Integer expectedNumber);

    /**
     * <p>
     * This method is used to increase stock of product
     * that user has cancelled
     * based on product, location and quantity Ordered
     * </p>
     *
     * @param quantity   - quantity to increase stock
     * @param product    - to which product
     * @param locationId - in which location
     */
    @Query("UPDATE Stock s SET s.availableStock = s.availableStock + ?1 "
            + "where s.product = ?2 AND s.storeLocation.id = ?3")
    @Modifying
    @Transactional
    void increaseStockByProductsAndLocation(Integer quantity,
                                            Product product,
                                            Integer locationId);
}
