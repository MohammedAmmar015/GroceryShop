package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.StoreLocationRequest;
import com.ideas2it.groceryshop.dto.StoreLocationResponse;
import com.ideas2it.groceryshop.mapper.StoreLocationMapper;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.repository.StoreRepo;
import com.ideas2it.groceryshop.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Implementation class for Store Service
 * </p>
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {
    private StoreRepo storeRepo;

    /**
     * <p>
     *     To add Store Location
     * </p>
     * @param storeLocationRequest request DTO to be passed
     */
    @Override
    public void addStore(StoreLocationRequest storeLocationRequest) {
        StoreLocation storeLocation = StoreLocationMapper.toStoreLocation(storeLocationRequest);
        storeRepo.save(storeLocation);
    }

    /**
     * <p>
     *     To get List of Stores
     * </p>
     * @return list of Store Location
     */
    @Override
    public List<StoreLocationResponse> getStores() {
        List<StoreLocationResponse> stores = new ArrayList<>();
        for (StoreLocation storeLocation : storeRepo.findByIsActive(true)) {
            stores.add(StoreLocationMapper.toStoreLocationResponse(storeLocation));
        }
        return stores;
    }

    /**
     * <p>
     *     To remove Store Location
     * </p>
     *
     * @param storeId store id has to be passes
     */
    @Override
    public void removeStore(Integer storeId) {
        storeRepo.deleteStoreById(storeId);
    }

    /**
     * <p>
     *     To get Particular Store details by StoreId
     * </p>
     * @param storeId store id has to be passed
     * @return StoreLocationResponse Object
     */
    @Override
    public StoreLocationResponse getStoreById(Integer storeId) {
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, storeId);
        StoreLocationResponse store = StoreLocationMapper.toStoreLocationResponse(storeLocation);
        return store;
    }

    /**
     * <p>
     *     To Modify Store Location details
     * </p>
     * @param storeLocationRequest Store Location details to update
     * @param storeId store id to be passed
     */
    @Override
    public void modifyStore(StoreLocationRequest storeLocationRequest, Integer storeId) {
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, storeId);
        storeLocation.setArea(storeLocationRequest.getArea());
        storeLocation.setPinCode(storeLocationRequest.getPinCode());
        storeRepo.save(storeLocation);
    }
}
