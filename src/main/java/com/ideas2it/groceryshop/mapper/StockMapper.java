package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.StockRequest;
import com.ideas2it.groceryshop.dto.StockResponse;
import com.ideas2it.groceryshop.model.Stock;

public class StockMapper {
    public static Stock toStock(StockRequest stockRequest) {
        Stock stock = new Stock();
        stock.setAvailableStock(stockRequest.getAvailableStock());
        return stock;
    }

    public static StockResponse toStockResponse(Stock stock) {
        StockResponse stockResponse = new StockResponse();
        stockResponse.setId(stock.getId());
        stockResponse.setAvailableStock(stock.getAvailableStock());
        stockResponse.setLocation(stock.getStoreLocation().getArea());
        stockResponse.setProductId(stock.getProduct().getId());
        stockResponse.setProductName(stock.getProduct().getName());
        stockResponse.setModifiedAt(stock.getModifiedAt());
        stockResponse.setModifiedBy(stock.getModifiedBy());
        return stockResponse;
    }
}
