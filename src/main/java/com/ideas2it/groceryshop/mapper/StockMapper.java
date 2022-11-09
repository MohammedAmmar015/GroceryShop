package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.StockRequest;
import com.ideas2it.groceryshop.dto.StockResponse;
import com.ideas2it.groceryshop.model.Stock;

/**
 * <p>
 *     Stock Mapper, used to convert Stock Entity and Stock DTO
 * </p>
 * @author Mohammed Ammar
 * @since 02-11-2022
 * @version 1.0
 */
public class StockMapper {

    /**
     * <p>
     *     It is used to convert StockRequest to Stock Entity
     * </p>
     * @param stockRequest - stock details
     * @return Stock
     */
    public static Stock toStock(StockRequest stockRequest) {
        Stock stock = new Stock();
        stock.setAvailableStock(stockRequest.getAvailableStock());
        return stock;
    }

    /**
     * <p>
     *     It is used to convert stock entity to Stock Response
     * </p>
     * @param stock - stock details
     * @return - StockResponse
     */
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
