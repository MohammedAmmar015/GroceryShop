package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StoreLocationRequest;
import com.ideas2it.groceryshop.dto.StoreLocationResponse;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     This is Store Location Controller to do Store related CRUD Operations
 * </p>
 * @author Mohammed Ammar
 * @since 04-11-2022
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/stores")
public class StoreLocationController {

    private StoreService storeService;

    /**
     * <p>
     *     This API is used to Create Store Location by Admin
     * </p>
     * @param storeLocationRequest - store location details
     */
    @PostMapping
    public void createStore(@RequestBody StoreLocationRequest storeLocationRequest) throws Existed {
        storeService.addStore(storeLocationRequest);
    }

    /**
     * <p>
     *     This API is used to Update Store Location details, like area
     * </p>
     * @param storeLocationRequest - store details to be updated
     * @param storeId - store id to update
     */
    @PutMapping("/{storeId}")
    public void updateStore(@RequestBody StoreLocationRequest storeLocationRequest,
                            @PathVariable Integer storeId) throws Existed, NotFoundException {
        storeService.modifyStore(storeLocationRequest, storeId);
    }

    /**
     * <p>
     *     This API is used to View List of Stores available
     * </p>
     * @return list of Store Location Response
     */
    @GetMapping
    public List<StoreLocationResponse> viewStores() throws NotFoundException {
        System.out.println(storeService.getStores());
        return storeService.getStores();
    }

    /**
     * <p>
     *     This API is used to view Particular Store Details
     * </p>
     * @param storeId - store id to view store details
     * @return - store response DTO
     */
    @GetMapping("/{storeId}")
    public StoreLocationResponse viewStoreById(@PathVariable Integer storeId) throws NotFoundException {
        return storeService.getStoreById(storeId);
    }

    /**
     * <p>
     *     This API is used to Delete Particular Store by store id
     * </p>
     * @param storeId - id to delete store
     */
    @DeleteMapping("/{storeId}")
    public void deleteStore(@PathVariable Integer storeId) {
        storeService.removeStore(storeId);
    }

}
