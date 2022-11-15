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
     * @return - SuccessDto if store created
     * @throws Existed - if area or location already exists
     */
    SuccessDto addStore(StoreRequestDto storeLocationRequest) throws Existed;

    /**
     * <p>
     *     This method is used to get all active stores
     * </p>
     * @return list of store response DTO
     * @throws NotFound if no data found
     */
    List<StoreResponseDto> getStores() throws NotFound;

    /**
     * <p>
     *     This method is used to remove store
     *     based on store location id
     * </p>
     * @param storeId store id has to be passed
     * @return - SuccessDto if deleted
     * @throws NotFound - if store not found
     */
    SuccessDto removeStore(Integer storeId) throws NotFound;


    /**
     * <p>
     *     This method is used get particular active store
     *     based on given location id
     * </p>
     * @param storeId store id has to be passed
     * @return Store Response DTO
     * @throws NotFound if store not found
     */
    StoreResponseDto getStoreById(Integer storeId) throws NotFound;

    /**
     * <P>
     *     This method is used to update store location details
     *     based on location id
     * </P>
     * @param storeLocationRequest Store Location details to update
     * @param storeId              store id to be passed
     * @return
     * @throws NotFound - if store not found
     * @throws Existed - if given new details already exist
     */
    SuccessDto modifyStore(StoreRequestDto storeLocationRequest,
                           Integer storeId) throws NotFound, Existed;
}
