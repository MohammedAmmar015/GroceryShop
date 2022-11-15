package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *     This is Store Location Rest Controller
 *     to do Store related CRUD Operations
 *     1. To create store location
 *     2. To update store location details
 *     3. To view all stores
 *     4. To view particular store by storeId
 *     5. To delete store by storeId
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
     *     This POST API api/v1/stores
     *     is used to Create Store Location
     * </p>
     * @param storeLocationRequest - store location details
     * @return SuccessResponseDto if store created successfully
     * @throws Existed if store details already exist
     */
    @PostMapping
    public SuccessResponseDto createStore(@Valid @RequestBody StoreRequestDto storeLocationRequest) throws Existed {
        return storeService.addStore(storeLocationRequest);
    }

    /**
     * <p>
     *     This PUT API api/v1/stores/{storeId}
     *     is used to Update Store Location details, like area
     * </p>
     * @param storeLocationRequest - store details to be updated
     * @param storeId - store id to update
     * @return SuccessResponseDto if store updated successfully
     * @throws NotFound if store not found
     * @throws Existed if details to be updated is already exists
     */
    @PutMapping("/{storeId}")
    public SuccessResponseDto updateStore(@Valid @RequestBody StoreRequestDto storeLocationRequest,
                                  @PathVariable Integer storeId) throws Existed, NotFound {
        return storeService.modifyStore(storeLocationRequest, storeId);
    }

    /**
     * <p>
     *     This GET API api/v1/stores
     *     is used to View List of Stores available
     * </p>
     * @return list of Store Location Response
     * @throws NotFound if no data found
     */
    @GetMapping
    public List<StoreResponseDto> viewStores() throws NotFound {
        return storeService.getStores();
    }

    /**
     * <p>
     *     This GET API api/v1/stores/{storeId}
     *     is used to view Particular Store Details
     * </p>
     * @param storeId - store id to view store details
     * @return - store response DTO
     * @throws NotFound if store not found
     */
    @GetMapping("/{storeId}")
    public StoreResponseDto viewStoreById(@PathVariable Integer storeId) throws NotFound {
        return storeService.getStoreById(storeId);
    }

    /**
     * <p>
     *     This DELETE API api/v1/stores/{storeId}
     *     is used to Delete Particular Store by store id
     * </p>
     * @param storeId id to delete store
     * @return SuccessResponseDto if store deleted successfully
     * @throws NotFound if store not found
     */
    @DeleteMapping("/{storeId}")
    public SuccessResponseDto deleteStore(@PathVariable Integer storeId) throws NotFound {
        return storeService.removeStore(storeId);
    }

}
