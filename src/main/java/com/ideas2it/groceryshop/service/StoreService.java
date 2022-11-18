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
     * </p>
     * @param storeLocationRequest request DTO to be passed
     * @return - SuccessResponseDto if store created
     * @throws ExistedException - if area or location already exists
     */
    SuccessResponseDto addStore(StoreRequestDto storeLocationRequest) throws ExistedException;

    /**
     * <p>
     *     This method is used to get all active stores
     * </p>
     * @return list of store response DTO
     * @throws NotFoundException if no data found
     */
    List<StoreResponseDto> getStores() throws NotFoundException;

    /**
     * <p>
     *     This method is used to remove store
     *     based on store location id
     * </p>
     * @param storeId store id has to be passed
     * @return - SuccessResponseDto if deleted
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

    StoreLocation getStoreById(Integer storeId)
            throws NotFoundException;

    /**
     * <P>
     *     This method is used to update store location details
     *     based on location id
     * </P>
     * @param storeLocationRequest Store Location details to update
     * @param storeId              store id to be passed
     * @return successResponseDto if store details modified successfully
     * @throws NotFoundException - if store not found
     * @throws ExistedException - if given new details already exist
     */
    SuccessResponseDto modifyStore(StoreRequestDto storeLocationRequest,
                           Integer storeId) throws NotFoundException, ExistedException;

    StoreLocation getStoreByPinCode(Integer pinCode);

    Boolean existByLocationId(Integer locationId);
}
