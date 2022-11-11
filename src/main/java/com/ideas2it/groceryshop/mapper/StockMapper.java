package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
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
    public static Stock toStock(StockRequestDto stockRequest) {
        Stock stock = new Stock();
        stock.setAvailableStock(stockRequest.getStock());
        return stock;
    }

    /**
     * <p>
     *     It is used to convert stock entity to Stock Response
     * </p>
     * @param stock - stock details
     * @return - StockResponse
     */
    public static StockResponseDto toStockResponse(Stock stock) {
        StockResponseDto stockResponse = new StockResponseDto();
        stockResponse.setId(stock.getId());
        stockResponse.setAvailableStock(stock.getAvailableStock());
        stockResponse.setArea(stock.getStoreLocation().getArea());
        stockResponse.setPinCode(stock.getStoreLocation().getPinCode());
        stockResponse.setProductId(stock.getProduct().getId());
        stockResponse.setProductName(stock.getProduct().getName());
        stockResponse.setSubCategory(stock.getProduct().getCategory().getName());
        stockResponse.setCategory(stock.getProduct().getCategory().getCategory().getName());
        return stockResponse;
    }
}
