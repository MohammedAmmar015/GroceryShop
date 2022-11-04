package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.StoreLocationRequest;
import com.ideas2it.groceryshop.dto.StoreLocationResponse;

import java.util.List;

public interface StoreService {
    void addStore(StoreLocationRequest storeLocationRequest);

    List<StoreLocationResponse> getStores();

    void removeStore(Integer storeId);


    StoreLocationResponse getStoreById(Integer storeId);

    void modifyStore(StoreLocationRequest storeLocationRequest, Integer storeId);
}
