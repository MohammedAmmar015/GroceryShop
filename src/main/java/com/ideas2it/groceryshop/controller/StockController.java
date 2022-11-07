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

    @PostMapping("/{locationId}/{productId}")
    public void createStock(@RequestBody StockRequest stockRequest,
                            @PathVariable("locationId") Integer locationId,
                            @PathVariable("productId") Integer productId) {
        stockService.addStock(stockRequest, locationId, productId);
    }

    @GetMapping("/{productId}")
    public List<StockResponse> viewStockByProduct(@PathVariable Integer productId) {
        return stockService.getStockByProductId(productId);
    }

    @GetMapping("/{productId}/{locationId}")
    public List<StockResponse> getStockByProductAndLocation(@PathVariable Integer productId,
                                                            @PathVariable Integer locationId) {
        return stockService.getStockByProductAndLocation(productId,locationId);
    }

    @PutMapping("/{productId}")
    public void updateStockByProduct(@RequestBody StockRequest stockRequest,
                                     @PathVariable Integer productId) {
        stockService.updateStockByProduct(stockRequest, productId);
    }

    @PutMapping("/{productId}/{locationId}")
    public void updateStockByProductAndLocation(@RequestBody StockRequest stockRequest,
                                                @PathVariable Integer productId,
                                                @PathVariable Integer locationId) {
        stockService.updateStockByProductAndLocation(stockRequest, productId, locationId);
    }
}
