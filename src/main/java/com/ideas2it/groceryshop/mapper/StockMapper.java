package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.StockRequest;
import com.ideas2it.groceryshop.model.Stock;

public class StockMapper {
    public static Stock toStock(StockRequest stockRequest) {
        Stock stock = new Stock();
        stock.setAvailableStock(stockRequest.getAvailableStock());
        return stock;
    }
}
