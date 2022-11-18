/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.StoreLocationMapper;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.repository.StoreRepo;
import com.ideas2it.groceryshop.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Implementation class for Store Service Interface
 *     This class implements methods which is used to 
 *     add store, update store location details,
 *     view stores, view particular store
 *     and delete store location
 * </p>
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final Logger logger = LogManager.getLogger(StoreServiceImpl.class);
    private final StoreRepo storeRepo;

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
    @Override
    public SuccessResponseDto addStore(StoreRequestDto storeLocationRequest)
                                       throws ExistedException {
        logger.debug("Entered addStore method in StoreServiceImpl");
        String area = storeLocationRequest.getArea();
        Integer pinCode = storeLocationRequest.getPinCode();
        if (storeRepo.existsByAreaOrPinCode(area, pinCode)) {
            logger.error("area or pin code already exist");
            throw new ExistedException("Area or PinCode already exists");
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
     * @return list of store as response DTO
     * @throws NotFoundException if no data found
     */
    @Override
    public List<StoreResponseDto> getStores() throws NotFoundException {
        logger.debug("Entered getStores method in StoreServiceImpl");
        List<StoreResponseDto> storesResponse = new ArrayList<>();
        List<StoreLocation> stores = storeRepo.findByIsActive(true);
        if (stores.isEmpty()) {
            logger.error("store not found");
            throw new NotFoundException("Store not found");
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
     * @return - SuccessResponseDto with success message and status code
     *          if store is deleted successfully
     * @throws NotFoundException - if store not found
     */
    @Override
    public SuccessResponseDto removeStore(Integer storeId) throws NotFoundException {
        logger.debug("Entered removeStore method in StoreServiceImpl");
        Integer rowsAffected = storeRepo.deleteStoreById(storeId);
        if (rowsAffected == 0) {
            logger.error("store not found");
            throw new NotFoundException("Store not found");
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
     * @throws NotFoundException if store not found
     */
    @Override
    public StoreResponseDto getStoreResponseById(Integer storeId)
                                         throws NotFoundException {
        logger.debug("Entered getStoreById method in StoreServiceImpl");
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, storeId);
        if (storeLocation == null) {
            logger.error("store not found");
            throw new NotFoundException("Store not found");
        }
        return StoreLocationMapper.toStoreLocationResponse(storeLocation);
    }

    @Override
    public StoreLocation getStoreById(Integer storeId)
            throws NotFoundException {
        logger.debug("Entered getStoreById method in StoreServiceImpl");
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, storeId);
        if (storeLocation == null) {
            logger.error("store not found");
            throw new NotFoundException("Store not found");
        }
        return storeLocation;
    }

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
    @Override
    public SuccessResponseDto modifyStore(StoreRequestDto storeLocationRequest,
                                          Integer storeId)
                                          throws NotFoundException, ExistedException {
        logger.debug("Entered modifyStore method in StoreServiceImpl");
        StoreLocation storeLocation
                        = storeRepo.findByIsActiveAndId(true, storeId);
        if (storeLocation == null) {
            logger.error("store not found");
            throw new NotFoundException("Store not found");
        }
        String area = storeLocationRequest.getArea();
        Integer pinCode = storeLocationRequest.getPinCode();
        if(storeRepo.existsByAreaOrPinCodeAndIdNot(area, pinCode, storeId)) {
            logger.error("area or pin code already exist");
            throw new ExistedException("Area or PinCode already exists");
        }
        storeLocation.setArea(storeLocationRequest.getArea());
        storeLocation.setPinCode(storeLocationRequest.getPinCode());
        storeRepo.save(storeLocation);
        logger.debug("store updated successfully");
        return new SuccessResponseDto(200,
                                "Store updated successfully");
    }

    @Override
    public StoreLocation getStoreByPinCode(Integer pinCode) {
        return storeRepo.findByIsActiveAndPinCode(true, pinCode);
    }

    /**
     * <p>
     *     This method used to check whether location id exist
     *     in database or not.
     * </p>
     * @param locationId for checking in database
     * @return true if locationId exist otherwise false.
     */
    @Override
    public Boolean existByLocationId(Integer locationId) {
        return storeRepo.existsByIdAndIsActive(locationId, true);
    }
}
