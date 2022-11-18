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
 *     This is Store Location Rest Controller
 *     to do Store related CRUD Operations
 *     1. api to create store location
 *     2. api to update store location details
 *     3. api to view all stores
 *     4. api to view particular store by storeId
 *     5. api to delete store by storeId
 * </p>
 * @author Mohammed Ammar
 * @since 04-11-2022
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
     *     This POST API {api/v1/stores}
     *     is used to Create Store Location
     * </p>
     * @param storeLocationRequest - store location details
     * @return SuccessResponseDto if store created successfully
     * @throws ExistedException if store details already exist
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
     *     This PUT API {api/v1/stores/{storeId}}
     *     is used to Update Store Location details, like area
     * </p>
     * @param storeLocationRequest - store details to be updated
     * @param storeId - store id to update
     * @return SuccessResponseDto if store updated successfully
     * @throws NotFoundException if store not found
     * @throws ExistedException if details to be updated is already exists
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
     *     This GET API {api/v1/stores}
     *     is used to View List of Stores available
     * </p>
     * @return list of Store Location Response
     * @throws NotFoundException if no data found
     */
    @GetMapping
    public List<StoreResponseDto> viewStores() throws NotFoundException {
        logger.debug("Entered viewStores method in StoreLocationController");
        return storeService.getStores();
    }

    /**
     * <p>
     *     This GET API {api/v1/stores/{storeId}}
     *     is used to view Particular Store Details
     * </p>
     * @param storeId - store id to view store details
     * @return - store response DTO
     * @throws NotFoundException if store not found
     */
    @GetMapping("/{storeId}")
    public StoreResponseDto viewStoreById(@PathVariable Integer storeId)
                                          throws NotFoundException {
        logger.debug("Entered viewStoreById method in StoreLocationController");
        return storeService.getStoreResponseById(storeId);
    }

    /**
     * <p>
     *     This DELETE API {api/v1/stores/{storeId}}
     *     is used to Delete Particular Store by store id
     * </p>
     * @param storeId id to delete store
     * @return SuccessResponseDto if store deleted successfully
     * @throws NotFoundException if store not found
     */
    @DeleteMapping("/{storeId}")
    public SuccessResponseDto deleteStore(@PathVariable Integer storeId)
                                          throws NotFoundException {
        logger.debug("Entered deleteStore method in StoreLocationController");
        return storeService.removeStore(storeId);
    }

}
