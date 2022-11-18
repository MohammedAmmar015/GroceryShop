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
 *     Interface for Store related Services
 *     This class is used to do add, get,
 *     update, delete stores location details
 *     using different methods
 * </p>
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
public interface StoreService {

    /**
     * <p>
     *     This method is used to add new Store Location
     *     based on storeRequestDTO
     * </p>
     * @param storeLocationRequest request DTO to be passed
     * @return - SuccessResponseDto with success message and status code
     *          if store created successfully
     * @throws ExistedException - if area or location already exists
     */
    SuccessResponseDto addStore(StoreRequestDto storeLocationRequest) throws ExistedException;

    /**
     * <p>
     *     This method is used to get all active stores
     * </p>
     * @return list of store as response DTO
     * @throws NotFoundException if no data found
     */
    List<StoreResponseDto> getStores() throws NotFoundException;

    /**
     * <p>
     *     This method is used to remove store
     *     based on store location id
     * </p>
     * @param storeId store id has to be passed
     * @return - SuccessResponseDto with success message and status code
     *          if store is deleted successfully
     * @throws NotFoundException - if store not found
     */
    SuccessResponseDto removeStore(Integer storeId) throws NotFoundException;


    /**
     * <p>
     *     This method is used get particular active store
     *     based on given location id
     * </p>
     * @param storeId store id has to be passed
     * @return Store Response DTO
     * @throws NotFoundException if store not found
     */
    StoreResponseDto getStoreResponseById(Integer storeId) throws NotFoundException;

    /**
     * <p>
     *     This method is used to get Store
     *     by location id
     * </p>
     * @param storeId - store location id to get store details
     * @return StoreLocation with store details
     * @throws NotFoundException
     */
    StoreLocation getStoreById(Integer storeId)
            throws NotFoundException;

    /**
     * <p>
     *     This method is used to update store location details
     *     based on location id
     * </p>
     * @param storeLocationRequest Store Location details to update
     * @param storeId              store id to be passed
     * @return successResponseDto if store details modified successfully
     * @throws NotFoundException - if store not found
     * @throws ExistedException - if given new details already exist
     */
    SuccessResponseDto modifyStore(StoreRequestDto storeLocationRequest,
                           Integer storeId) throws NotFoundException, ExistedException;

    /**
     * <p>
     *     This method is used to get store details
     *     based on given pinCode
     * </p>
     * @param pinCode store location pinCode to get store details
     * @return store location with area and pinCode
     */
    StoreLocation getStoreByPinCode(Integer pinCode);

    /**
     * <p>
     *     This method used to check whether location id exist
     *     in database or not.
     * </p>
     * @param locationId for checking in database
     * @return true if locationId exist otherwise false.
     */
    Boolean existByLocationId(Integer locationId);
}
