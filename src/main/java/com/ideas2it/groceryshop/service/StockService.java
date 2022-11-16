/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.model.UserOrder;

import java.util.List;

/**
 * <p>
 *     Interface for Stock related Services
 *     This class is used to do add, update,
 *     get stock details for different product and location
 * </p>
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
public interface StockService {

    /**
     * <p>
     * It is used to add Stock for Particular product based on Location
     * </p>
     *
     * @param stockRequest stock details to add
     * @param locationId   To add stock to particular location
     * @param productId    To add Stock to the Product
     * @return SuccessResponseDto if stock created
     * @throws NotFound if store or product not found
     * @throws Existed if stock already exist for given product and location
     */
    SuccessResponseDto addStock(StockRequestDto stockRequest, Integer locationId, Integer productId)
            throws NotFound, Existed;

    /**
     * <p>
     *     It is used to get Stock details for particular product,
     *     on different location
     * </p>
     * @param productId id to view Stock details
     * @return List of Stock details
     * @throws NotFound if stock not found for given product id
     */
    List<StockResponseDto> getStockByProductId(Integer productId)
            throws NotFound;

    /**
     * <p>
     *     To view stock details, for particular product on particular location
     * </p>
     * @param productId - to view Stock details
     * @param locationId - to view stock by product and location
     * @return Store Response DTO
     * @throws NotFound if stock not found for given ids
     */
    StockResponseDto getStockByProductAndLocation(Integer productId, Integer locationId)
            throws NotFound;


    /**
     * <p>
     *     To update stock for particular product on particular location
     * </p>
     *
     * @param stockRequest - stock details to update
     * @param productId    - id to update stock for this product
     * @param locationId   - id to update stock on this location
     * @return SuccessResponseDto if Stock updated
     * @throws NotFound if stock not found for product id and location id
     */
    SuccessResponseDto updateStockByProductAndLocation(StockRequestDto stockRequest,
                                               Integer productId,
                                               Integer locationId)
            throws NotFound;

    /**
     * <p>
     *     This method is used to decrease stock by
     *     user order details, and location details
     * </p>
     * @param order it has product details, user ordered
     * @param store store location that user ordered for
     */
    void removeStockByOrderDetails(UserOrder order, StoreLocation store);

    void updateStockByOrderDetails(UserOrder order, StoreLocation store);
}
