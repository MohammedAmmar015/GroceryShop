package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.StoreService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StoreLocationControllerTest {

    @InjectMocks
    private StoreLocationController storeLocationController;

    @Mock
    private StoreService storeService;

    @Test
    public void testCreateStore() throws Existed {
        StoreRequestDto storeRequestDto = new StoreRequestDto(600001, "Guindy");
        SuccessDto successDto = new SuccessDto(201, "Store Created Successfully");
        when(storeService.addStore(storeRequestDto)).thenReturn(successDto);
        SuccessDto result = storeLocationController.createStore(storeRequestDto);
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }

    @Test
    public void testViewStores() throws NotFound {
        List<StoreResponseDto> stores = new ArrayList<>();
        StoreResponseDto storeOne=
                new StoreResponseDto(1, 600001, "Guindy");
        StoreResponseDto storeTwo =
                new StoreResponseDto(1, 600002, "Anna Nagar");
        stores.add(storeOne);
        stores.add(storeTwo);
        when(storeService.getStores()).thenReturn(stores);
        assertEquals(stores.size(), storeLocationController.viewStores().size());
    }

    @Test
    public void testViewStoreById() throws NotFound {
        Integer storeId = 1 ;
        StoreResponseDto store=
                new StoreResponseDto(1, 600001, "Guindy");
        when(storeService.getStoreById(storeId)).thenReturn(store);
        assertEquals(storeId, storeLocationController.viewStoreById(storeId).getId());
    }



    @Test
    public void testUpdateStore() throws Existed, NotFound {
        Integer storeId = 1;
        StoreRequestDto storeRequestDto = new StoreRequestDto(600001, "Guindy");
        SuccessDto successDto = new SuccessDto(200, "Store Updated Successfully");
        when(storeService.modifyStore(storeRequestDto, storeId)).thenReturn(successDto);
        SuccessDto result = storeLocationController.updateStore(storeRequestDto, storeId);
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }

    @Test
    public void testDeleteStore() throws Existed, NotFound {
        Integer storeId = 1;
        SuccessDto successDto = new SuccessDto(200, "Store Deleted Successfully");
        when(storeService.removeStore(storeId)).thenReturn(successDto);
        SuccessDto result = storeLocationController.deleteStore(storeId);
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }
}
