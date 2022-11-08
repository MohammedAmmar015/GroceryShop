package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.StoreLocationRequest;
import com.ideas2it.groceryshop.dto.StoreLocationResponse;

import java.util.List;

/**
 * <p>
 *     Interface for Store related Services
 * </p>
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
public interface StoreService {

    /**
     * <p>
     *     To add Store Location
     * </p>
     * @param storeLocationRequest request DTO to be passed
     */
    void addStore(StoreLocationRequest storeLocationRequest);

    /**
     * <p>
     *     To get List of Stores
     * </p>
     * @return list of Store Location
     */
    List<StoreLocationResponse> getStores();

    /**
     * <p>
     *     To remove Store Location
     * </p>
     *
     * @param storeId store id has to be passes
     */
    void removeStore(Integer storeId);


    /**
     * <p>
     *     To get Particular Store details by StoreId
     * </p>
     * @param storeId store id has to be passed
     * @return StoreLocationResponse Object
     */
    StoreLocationResponse getStoreById(Integer storeId);

    /**
     * <p>
     *     To Modify Store Location details
     * </p>
     * @param storeLocationRequest Store Location details to update
     * @param storeId store id to be passed
     */
    void modifyStore(StoreLocationRequest storeLocationRequest, Integer storeId);
}
