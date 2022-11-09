package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.model.StoreLocation;

/**
 * <p>
 *     Store Location Mapper, used to convert StoreLocation Entity and StoreLocation DTO
 * </p>
 * @author Mohammed Ammar
 * @since 02-11-2022
 * @version 1.0
 */
public class StoreLocationMapper {

    /**
     * <p>
     *     It is used to convert StoreLocation Request to StoreLocation Entity
     * </p>
     * @param storeLocationRequest - store details
     * @return - StoreLocation
     */
    public static StoreLocation toStoreLocation(StoreRequestDto storeLocationRequest) {
        StoreLocation storeLocation = new StoreLocation();
        storeLocation.setArea(storeLocationRequest.getArea());
        storeLocation.setPinCode(storeLocationRequest.getPinCode());
        return storeLocation;
    }

    /**
     * <p>
     *     It is used to convert StoreLocation Entity to StoreLocation Response
     * </p>
     * @param storeLocation - store location details
     * @return - StoreLocationResponse
     */
    public static StoreResponseDto toStoreLocationResponse(StoreLocation storeLocation) {
        StoreResponseDto stores = new StoreResponseDto();
        stores.setId(storeLocation.getId());
        stores.setArea(storeLocation.getArea());
        stores.setPinCode(storeLocation.getPinCode());
        stores.setIsActive(storeLocation.getIsActive());
        stores.setCreatedAt(storeLocation.getCreatedAt());
        return stores;
    }
}
