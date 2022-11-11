package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
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
    public SuccessDto createStore(@RequestBody StoreRequestDto storeLocationRequest) throws Existed {
        return storeService.addStore(storeLocationRequest);
    }

    /**
     * <p>
     *     This API is used to Update Store Location details, like area
     * </p>
     * @param storeLocationRequest - store details to be updated
     * @param storeId - store id to update
     */
    @PutMapping("/{storeId}")
    public SuccessDto updateStore(@RequestBody StoreRequestDto storeLocationRequest,
                                  @PathVariable Integer storeId) throws Existed, NotFound {
        return storeService.modifyStore(storeLocationRequest, storeId);
    }

    /**
     * <p>
     *     This API is used to View List of Stores available
     * </p>
     * @return list of Store Location Response
     */
    @GetMapping
    public List<StoreResponseDto> viewStores() throws NotFound {
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
    public StoreResponseDto viewStoreById(@PathVariable Integer storeId) throws NotFound {
        return storeService.getStoreById(storeId);
    }

    /**
     * <p>
     *     This API is used to Delete Particular Store by store id
     * </p>
     * @param storeId - id to delete store
     */
    @DeleteMapping("/{storeId}")
    public SuccessDto deleteStore(@PathVariable Integer storeId) throws NotFound {
        return storeService.removeStore(storeId);
    }

}
