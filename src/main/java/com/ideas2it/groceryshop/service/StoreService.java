package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;

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
     * To add Store Location
     * </p>
     *
     * @param storeLocationRequest request DTO to be passed
     * @return
     */
    SuccessDto addStore(StoreRequestDto storeLocationRequest) throws Existed;

    /**
     * <p>
     *     To get List of Stores
     * </p>
     * @return list of Store Location
     */
    List<StoreResponseDto> getStores() throws NotFound;

    /**
     * <p>
     * To remove Store Location
     * </p>
     *
     * @param storeId store id has to be passes
     * @return
     */
    SuccessDto removeStore(Integer storeId) throws NotFound;


    /**
     * <p>
     *     To get Particular Store details by StoreId
     * </p>
     * @param storeId store id has to be passed
     * @return StoreLocationResponse Object
     */
    StoreResponseDto getStoreById(Integer storeId) throws NotFound;

    /**
     * <p>
     * To Modify Store Location details
     * </p>
     *
     * @param storeLocationRequest Store Location details to update
     * @param storeId              store id to be passed
     * @return
     */
    SuccessDto modifyStore(StoreRequestDto storeLocationRequest,
                           Integer storeId) throws NotFound, Existed;
}
