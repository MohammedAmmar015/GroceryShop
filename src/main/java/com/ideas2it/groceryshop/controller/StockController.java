package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StockRequest;
import com.ideas2it.groceryshop.dto.StockResponse;
import com.ideas2it.groceryshop.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/{locationId}")
    public void createStock(@RequestBody StockRequest stockRequest,
                            @PathVariable Integer locationId) {

    }

    @GetMapping("/{productId}")
    public List<StockResponse> getStockByProduct() {
        return new ArrayList<>();
    }

    @GetMapping("/{productId}/{locationId}")
    public List<StockResponse> getStockByProductAndLocation() {
        return new ArrayList<>();
    }

    @PutMapping("/{productId}")
    public void updateStockByProduct(@RequestBody StockRequest stockRequest) {

    }

    @PutMapping("/{productId}/{location-id}")
    public void updateStockByProductAndLocation(@RequestBody StockRequest stockRequest) {

    }
}
