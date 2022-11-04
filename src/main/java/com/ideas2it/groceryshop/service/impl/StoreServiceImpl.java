package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.StoreLocationRequest;
import com.ideas2it.groceryshop.dto.StoreLocationResponse;
import com.ideas2it.groceryshop.mapper.StoreLocationMapper;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.model.User;
import com.ideas2it.groceryshop.repository.StoreRepo;
import com.ideas2it.groceryshop.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private StoreRepo storeRepo;

    @Autowired
    public StoreServiceImpl(StoreRepo storeRepo) {
        this.storeRepo = storeRepo;
    }

    @Override
    public void addStore(StoreLocationRequest storeLocationRequest) {
        StoreLocation storeLocation = StoreLocationMapper.toStoreLocation(storeLocationRequest);
        storeRepo.save(storeLocation);
    }

    @Override
    public List<StoreLocationResponse> getStores() {
        List<StoreLocationResponse> stores = new ArrayList<>();
        for (StoreLocation storeLocation : storeRepo.findByIsActive(true)) {
            stores.add(StoreLocationMapper.toStoreLocationResponse(storeLocation));
        }
        return stores;
    }

    @Override
    public void removeStore(Integer storeId) {
        storeRepo.deleteStoreById(storeId);
    }

    @Override
    public StoreLocationResponse getStoreById(Integer storeId) {
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, storeId);
        StoreLocationResponse store = StoreLocationMapper.toStoreLocationResponse(storeLocation);
        return store;
    }

    @Override
    public void modifyStore(StoreLocationRequest storeLocationRequest, Integer storeId) {
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, storeId);
        storeLocation.setArea(storeLocationRequest.getArea());
        storeLocation.setPinCode(storeLocationRequest.getPinCode());
        storeRepo.save(storeLocation);
    }
}
