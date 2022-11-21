/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.model.StoreLocation;

import java.util.List;

/**
 * <p>
 *     Provide services to add, update, view and delete Store Location
 *     (like area, pin code,. etc)
 * </p>
 *
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
public interface StoreService {

    /**
     * <p>
     *     Creates store location based on given location detail
     * </p>
     *
     * @param storeLocationRequest - Contains area and pin code to create store
     * @return SuccessResponseDto - Contains success message and status code
     * @throws ExistedException - If area or location already exist
     */
    SuccessResponseDto addStore(StoreRequestDto storeLocationRequest) throws ExistedException;

    /**
     * <p>
     *     Get all active stores location detail
     * </p>
     *
     * @return list of storeResponseDTO - Contains area, pin code, etc., for available stores
     * @throws NotFoundException - If no store location found
     */
    List<StoreResponseDto> getStores() throws NotFoundException;

    /**
     * <p>
     *      Remove store based on given store location id
     * </p>
     *
     * @param storeId - To remove store for given store
     * @return SuccessResponseDto - Contains success message and status code
     * @throws NotFoundException - If store not found
     */
    SuccessResponseDto removeStore(Integer storeId) throws NotFoundException;


    /**
     * <p>
     *     Get particular store detail as response dto based on given location id
     * </p>
     *
     * @param storeId - To get particular store detail
     * @return StoreResponseDTO - Contains success message and status code
     * @throws NotFoundException - If store not found
     */
    StoreResponseDto getStoreResponseById(Integer storeId) throws NotFoundException;

    /**
     * <p>
     *     Get particular active store based on given location id
     * </p>
     *
     * @param storeId - To get particular store location
     * @return StoreLocation - Contains store detail like area and pin code
     * @throws NotFoundException - If store not found
     */
    StoreLocation getStoreById(Integer storeId) throws NotFoundException;

    /**
     * <p>
     *     Update store location detail with given new detail
     *     based on given location id
     * </p>
     *
     * @param storeLocationRequest - Contains store location detail to update
     * @param storeId - To update store detail for given store
     * @return successResponseDto - Contains success message and status code
     * @throws NotFoundException - If store not found
     * @throws ExistedException - If given new details already exist
     */
    SuccessResponseDto modifyStore(StoreRequestDto storeLocationRequest, Integer storeId)
                                   throws NotFoundException, ExistedException;

    /**
     * <p>
     *     Get store details based on given pinCode
     * </p>
     *
     * @param pinCode - To get store details for given pin code
     * @return storeLocation - Contains area and pinCode
     */
    StoreLocation getStoreByPinCode(Integer pinCode);

    /**
     * <p>
     *     Check whether store location exist or not.
     * </p>
     *
     * @param locationId - To check if store exist
     * @return true if locationId exist otherwise false.
     */
    Boolean existByLocationId(Integer locationId);
}
