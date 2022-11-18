/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.StoreLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>
 * This is StoreRepository which extends JpaRepository,
 * by default we can use default methods available in JpaRepository,
 * We can also add our custom methods in this repository to do
 * database operations on Store Entity
 * </p>
 *
 * @author Mohammed Ammar
 * @version 1.0
 * @since 03-11-2022
 */
@Repository
public interface StoreRepo extends JpaRepository<StoreLocation, Integer> {

    /**
     * <p>
     *  This method is used to retrieve
     *  active or deleted Stores based on isActive
     * </p>
     *
     * @param isActive deleted or active store
     * @return list of stores based on is active
     */
    List<StoreLocation> findByIsActive(Boolean isActive);

    /**
     * <p>
     * This method is used to retrieve particular
     * active or deleted Store based on location id
     * </p>
     *
     * @param isActive deleted or active store
     * @param id       - location id
     * @return - Store Location Object
     */
    StoreLocation findByIsActiveAndId(Boolean isActive, Integer id);

    /**
     * <p>
     * This method is used to delete store location
     * based on location id
     * </p>
     *
     * @param storeId - id to delete
     * @return - number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE StoreLocation SET isActive = false WHERE id = ?1")
    Integer deleteStoreById(Integer storeId);

    /**
     * <p>
     * This method is used to check if area or pin code
     * is already exist in database or not
     * </p>
     *
     * @param area    - area details
     * @param pinCode - area Pin code
     * @return true if exists else false
     */
    Boolean existsByAreaOrPinCode(String area,
                                  Integer pinCode);

    /**
     * <p>
     * This method is used to check if location id
     * is already exist and active or not in database
     * </p>
     *
     * @param locationId - location id to check
     * @param status     - true or false
     * @return - true if store exist else false
     */
    Boolean existsByIdAndIsActive(Integer locationId,
                                  Boolean status);

    /**
     * <p>
     * This method is used to retrieve active or deleted Store
     * based on Pin code
     * </p>
     *
     * @param status  true or false
     * @param pinCode - area pin code to retrieve
     * @return storeLocation based on status and pinCode
     */
    StoreLocation findByIsActiveAndPinCode(Boolean status,
                                           Integer pinCode);

    /**
     * <p>
     * This method is used to check if area or pin code
     * is already exist or not except the given id
     * </p>
     *
     * @param area    - area name
     * @param pinCode pin code number
     * @param storeId - location id
     * @return - true if exist else false
     */
    Boolean existsByAreaOrPinCodeAndIdNot(String area,
                                          Integer pinCode,
                                          Integer storeId);
}
