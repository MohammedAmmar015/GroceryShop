/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.mapper.StoreLocationMapper;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.repository.StoreRepo;
import com.ideas2it.groceryshop.service.StoreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Implementation class for Store Service
 *     This class is used to do add, get,
 *     update, delete stores location details
 *     using different methods
 * </p>
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@Service
public class StoreServiceImpl implements StoreService {

    private final Logger logger;
    private final StoreRepo storeRepo;

    public StoreServiceImpl(StoreRepo storeRepo) {
        this.logger = LogManager.getLogger(StoreServiceImpl.class);
        this.storeRepo = storeRepo;
    }

    /**
     * <p>
     *     This method is used to add new Store Location
     *     based on storeRequestDTO
     * </p>
     * @param storeLocationRequest request DTO to be passed
     * @return - SuccessResponseDto if store created
     * @throws Existed - if area or location already exists
     */
    @Override
    public SuccessResponseDto addStore(StoreRequestDto storeLocationRequest)
                                       throws Existed {
        logger.debug("Entered addStore method in StoreServiceImpl");
        String area = storeLocationRequest.getArea();
        Integer pinCode = storeLocationRequest.getPinCode();
        if (storeRepo.existsByAreaOrPinCode(area, pinCode)) {
            logger.error("area or pin code already exist");
            throw new Existed("Area or PinCode already exists");
        }
        StoreLocation storeLocation
                        = StoreLocationMapper.toStoreLocation(storeLocationRequest);
        storeRepo.save(storeLocation);
        logger.debug("store created successfully");
        return new SuccessResponseDto(201, "Store created successfully");
    }

    /**
     * <p>
     *     This method is used to get all active stores
     * </p>
     * @return list of store response DTO
     * @throws NotFound if no data found
     */
    @Override
    public List<StoreResponseDto> getStores() throws NotFound {
        logger.debug("Entered getStores method in StoreServiceImpl");
        List<StoreResponseDto> storesResponse = new ArrayList<>();
        List<StoreLocation> stores = storeRepo.findByIsActive(true);
        if (stores.isEmpty()) {
            logger.error("store not found");
            throw new NotFound("Store not found");
        }
        for (StoreLocation storeLocation : stores) {
            storesResponse.add(StoreLocationMapper.toStoreLocationResponse(storeLocation));
        }
        return storesResponse;
    }

    /**
     * <p>
     *     This method is used to remove store
     *     based on store location id
     * </p>
     * @param storeId store id has to be passed
     * @return - SuccessResponseDto if deleted
     * @throws NotFound - if store not found
     */
    @Override
    public SuccessResponseDto removeStore(Integer storeId) throws NotFound {
        logger.debug("Entered removeStore method in StoreServiceImpl");
        Integer rowsAffected = storeRepo.deleteStoreById(storeId);
        if (rowsAffected == 0) {
            logger.error("store not found");
            throw new NotFound("Store not found");
        }
        logger.debug("store deleted successfully");
        return new SuccessResponseDto(200, "Store deleted successfully");
    }

    /**
     * <p>
     *     This method is used get particular active store
     *     based on given location id
     * </p>
     * @param storeId store id has to be passed
     * @return Store Response DTO
     * @throws NotFound if store not found
     */
    @Override
    public StoreResponseDto getStoreById(Integer storeId)
                                         throws NotFound {
        logger.debug("Entered getStoreById method in StoreServiceImpl");
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, storeId);
        if (storeLocation == null) {
            logger.error("store not found");
            throw new NotFound("Store not found");
        }
        return StoreLocationMapper.toStoreLocationResponse(storeLocation);
    }

    /**
     * <P>
     *     This method is used to update store location details
     *     based on location id
     * </P>
     * @param storeLocationRequest Store Location details to update
     * @param storeId              store id to be passed
     * @return successResponseDto if store details modified successfully
     * @throws NotFound - if store not found
     * @throws Existed - if given new details already exist
     */
    @Override
    public SuccessResponseDto modifyStore(StoreRequestDto storeLocationRequest,
                                          Integer storeId)
                                          throws NotFound, Existed {
        logger.debug("Entered modifyStore method in StoreServiceImpl");
        StoreLocation storeLocation
                        = storeRepo.findByIsActiveAndId(true, storeId);
        if (storeLocation == null) {
            logger.error("store not found");
            throw new NotFound("Store not found");
        }
        String area = storeLocationRequest.getArea();
        Integer pinCode = storeLocationRequest.getPinCode();
        if(storeRepo.existsByAreaOrPinCodeAndIdNot(area, pinCode, storeId)) {
            logger.error("area or pin code already exist");
            throw new Existed("Area or PinCode already exists");
        }
        storeLocation.setArea(storeLocationRequest.getArea());
        storeLocation.setPinCode(storeLocationRequest.getPinCode());
        storeRepo.save(storeLocation);
        logger.debug("store updated successfully");
        return new SuccessResponseDto(200,
                                "Store updated successfully");
    }
}
