package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.StockRequest;
import com.ideas2it.groceryshop.dto.StockResponse;

import java.util.List;

public interface StockService {

    void addStock(StockRequest stockRequest, Integer locationId, Integer productId);

    List<StockResponse> getStockByProductId(Integer productId);

    List<StockResponse> getStockByProductAndLocation(Integer productId, Integer locationId);

    void updateStockByProduct(StockRequest stockRequest, Integer productId);

    void updateStockByProductAndLocation(StockRequest stockRequest, Integer productId, Integer locationId);
}
