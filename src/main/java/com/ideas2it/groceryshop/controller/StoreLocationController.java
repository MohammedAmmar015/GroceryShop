package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StoreLocationRequest;
import com.ideas2it.groceryshop.dto.StoreLocationResponse;
import com.ideas2it.groceryshop.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/stores")
public class StoreLocationController {

    private StoreService storeService;

    @Autowired
    public StoreLocationController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public void createStore(@RequestBody StoreLocationRequest storeLocationRequest) {
        storeService.addStore(storeLocationRequest);
    }

    @PutMapping("/{storeId}")
    public void updateStore(@RequestBody StoreLocationRequest storeLocationRequest,
                            @PathVariable Integer storeId) {
        storeService.modifyStore(storeLocationRequest, storeId);
    }

    @GetMapping
    public List<StoreLocationResponse> viewStores() {
        System.out.println(storeService.getStores());
        return storeService.getStores();
    }

    @GetMapping("/{storeId}")
    public StoreLocationResponse viewStoreById(@PathVariable Integer storeId) {
        return storeService.getStoreById(storeId);
    }

    @DeleteMapping("/{storeId}")
    public void deleteStore(@PathVariable Integer storeId) {
        storeService.removeStore(storeId);
    }

}
