package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.StockRequest;

public interface StockService {
    void addStock(StockRequest stockRequest, Integer locationId);
}
