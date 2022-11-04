package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.StoreLocationRequest;
import com.ideas2it.groceryshop.dto.StoreLocationResponse;
import com.ideas2it.groceryshop.model.StoreLocation;

public class StoreLocationMapper {
    public static StoreLocation toStoreLocation(StoreLocationRequest storeLocationRequest) {
        StoreLocation storeLocation = new StoreLocation();
        storeLocation.setArea(storeLocationRequest.getArea());
        storeLocation.setPinCode(storeLocationRequest.getPinCode());
        return storeLocation;
    }

    public static StoreLocationResponse toStoreLocationResponse(StoreLocation storeLocation) {
        StoreLocationResponse stores = new StoreLocationResponse();
        stores.setId(storeLocation.getId());
        stores.setArea(storeLocation.getArea());
        stores.setPinCode(storeLocation.getPinCode());
        stores.setIsActive(storeLocation.getIsActive());
        stores.setCreatedAt(storeLocation.getCreatedAt());
        return stores;
    }
}
