package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.mapper.StoreLocationMapper;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.repository.StoreRepo;
import com.ideas2it.groceryshop.service.StoreService;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
     * To add Store Location
     * </p>
     *
     * @param storeLocationRequest request DTO to be passed
     * @return
     */
    @Override
    public SuccessDto addStore(StoreRequestDto storeLocationRequest) throws Existed {
        String area = storeLocationRequest.getArea();
        Integer pinCode = storeLocationRequest.getPinCode();
        if (storeRepo.existsByAreaOrPinCode(area, pinCode)) {
            throw new Existed("Area or PinCode Already exists");
        }
        StoreLocation storeLocation = StoreLocationMapper.toStoreLocation(storeLocationRequest);
        storeRepo.save(storeLocation);
        return new SuccessDto(201, "Store Location Created Successfully");
    }

    /**
     * <p>
     *     To get List of Stores
     * </p>
     * @return list of Store Location
     */
    @Override
    public List<StoreResponseDto> getStores() throws NotFound {
        List<StoreResponseDto> storesResponse = new ArrayList<>();
        List<StoreLocation> stores = storeRepo.findByIsActive(true);
        if (stores.isEmpty()) {
            throw new NotFound("No Store Locations Found");
        }
        for (StoreLocation storeLocation : stores) {
            storesResponse.add(StoreLocationMapper.toStoreLocationResponse(storeLocation));
        }
        return storesResponse;
    }

    /**
     * <p>
     * To remove Store Location
     * </p>
     *
     * @param storeId store id has to be passes
     * @return
     */
    @Override
    public SuccessDto removeStore(Integer storeId) throws NotFound {
        Integer rowsAffected = storeRepo.deleteStoreById(storeId);
        if (rowsAffected == 0) {
            throw new NotFound("Given Store id Not Found");
        }
        return new SuccessDto(200, "Store Location deleted Successfully");
    }


    /**
     * <p>
     *     To get Particular Store details by StoreId
     * </p>
     * @param storeId store id has to be passed
     * @return StoreLocationResponse Object
     */
    @Override
    public StoreResponseDto getStoreById(Integer storeId) throws NotFound {
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, storeId);
        if (storeLocation == null) {
            throw new NotFound("Given Store id Not Found");
        }
        StoreResponseDto storeResponse = StoreLocationMapper.toStoreLocationResponse(storeLocation);
        return storeResponse;
    }

    /**
     * <p>
     * To Modify Store Location details
     * </p>
     *
     * @param storeLocationRequest Store Location details to update
     * @param storeId              store id to be passed
     * @return
     */
    @Override
    public SuccessDto modifyStore(StoreRequestDto storeLocationRequest,
                                  Integer storeId) throws NotFound, Existed {
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, storeId);
        String area = storeLocationRequest.getArea();
        Integer pinCode = storeLocationRequest.getPinCode();
        if(storeRepo.existsByAreaOrPinCode(area, pinCode)) {
            throw new Existed("Area or PinCode already exists");
        }
        if (storeLocation == null) {
            throw new NotFound("Given Store id for Update, Not Found");
        }
        storeLocation.setArea(storeLocationRequest.getArea());
        storeLocation.setPinCode(storeLocationRequest.getPinCode());
        storeRepo.save(storeLocation);
        return new SuccessDto(200, "Store Location Updated Successfully");
    }
}
