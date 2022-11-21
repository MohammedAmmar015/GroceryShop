/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *     Provides APIs to add, update, view and delete
 *     Store Location (like area, pin code,. etc)
 * </p>
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/stores")
public class StoreLocationController {
    
    private final Logger logger = LogManager.getLogger(StoreLocationController.class);
    private final StoreService storeService;

    /**
     * <p>
     *     Creates store location by getting storeRequest
     *     details from user
     * </p>
     * @param storeLocationRequest - contains area and pin code to create store
     * @return SuccessResponseDto - contains success message and status code
     * @throws ExistedException - if area or location already exist
     */
    @PostMapping
    public SuccessResponseDto createStore
                              (@Valid @RequestBody StoreRequestDto storeLocationRequest)
                               throws ExistedException {
        logger.debug("Entered createStore method in StoreLocationController");
        return storeService.addStore(storeLocationRequest);
    }

    /**
     * <p>
     *     Update store location detail by getting new storeRequest detail
     *     and locationId from user
     * </p>
     * @param storeLocationRequest - contains store location detail to update
     * @param storeId - to update store detail for given storeId
     * @return successResponseDto - contains message and status code
     * @throws NotFoundException - if store not found
     * @throws ExistedException - if given new details already exist
     */
    @PutMapping("/{storeId}")
    public SuccessResponseDto updateStore
                              (@Valid @RequestBody StoreRequestDto storeLocationRequest,
                               @PathVariable Integer storeId) throws ExistedException, NotFoundException {
        logger.debug("Entered updateStore method in StoreLocationController");
        return storeService.modifyStore(storeLocationRequest, storeId);
    }

    /**
     * <p>
     *     View all active stores location detail as ResponseDto to user
     * </p>
     * @return list of storeResponseDTO - contains area, pin code, etc., for available stores
     * @throws NotFoundException - if no store location found
     */
    @GetMapping
    public List<StoreResponseDto> viewStores() throws NotFoundException {
        logger.debug("Entered viewStores method in StoreLocationController");
        return storeService.getStores();
    }

    /**
     * <p>
     *     View particular store detail as response dto based
     *     by getting location id from user
     * </p>
     * @param storeId - to get particular store detail
     * @return StoreResponseDTO - contains success message and status code
     * @throws NotFoundException - if store not found
     */
    @GetMapping("/{storeId}")
    public StoreResponseDto viewStoreById(@PathVariable Integer storeId)
                                          throws NotFoundException {
        logger.debug("Entered viewStoreById method in StoreLocationController");
        return storeService.getStoreResponseById(storeId);
    }

    /**
     * <p>
     *      Remove store by getting store location id from user
     * </p>
     * @param storeId - to remove store for given store id
     * @return SuccessResponseDto - contains success message and status code
     * @throws NotFoundException - if store not found
     */
    @DeleteMapping("/{storeId}")
    public SuccessResponseDto deleteStore(@PathVariable Integer storeId)
                                          throws NotFoundException {
        logger.debug("Entered deleteStore method in StoreLocationController");
        return storeService.removeStore(storeId);
    }

}
