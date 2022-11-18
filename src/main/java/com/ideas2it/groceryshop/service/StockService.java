/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
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
     *     This method is used to add Stock for particular product
     *     based on Location id
     *     stockRequest has stock details to add stock for product
     * </p>
     *
     * @param stockRequest stock details to add stock
     * @param locationId   To add stock to particular location
     * @param productId    To add Stock to the Product
     * @return SuccessResponseDto with success message and status code
     *          if stock created
     * @throws NotFoundException if store or product not found
     * @throws ExistedException if stock already exist for given product and location
     */
    SuccessResponseDto addStock(StockRequestDto stockRequest, Integer locationId, Integer productId)
            throws NotFoundException, ExistedException;

    /**
     * <p>
     *     This method is used to get available Stock details
     *     for particular product,
     *     available on different location
     *     It will return Stock details as list Response DTO
     * </p>
     * @param productId id to view Stock details of product
     * @return List of Stock details on different location
     * @throws NotFoundException if stock not found for given product id
     */
    List<StockResponseDto> getStockByProductId(Integer productId)
            throws NotFoundException;

    /**
     * <p>
     *     This method is used to view stock details,
     *     for particular product on particular location
     *     based on product id and location id
     *     It will return available stock details
     *     as response DTO
     * </p>
     * @param productId - to view Stock details of product
     * @param locationId - to view stock by location
     * @return Store Response DTO with available stock
     * @throws NotFoundException if stock not found for given id
     */
    StockResponseDto getStockByProductAndLocation(Integer productId, Integer locationId)
            throws NotFoundException;


    /**
     * <p>
     *     This method is used to update stock
     *     for particular product on particular location
     *     based on product id and location id
     *     stockRequest has stock details to be added
     *     to stock
     * </p>
     *
     * @param stockRequest - stock details to update
     * @param productId    - id to update stock for this product
     * @param locationId   - id to update stock on this location
     * @return SuccessResponseDto success message with status code
     *          if Stock updated
     * @throws NotFoundException if stock not found for product id and location id
     */
    SuccessResponseDto updateStockByProductAndLocation(StockRequestDto stockRequest,
                                               Integer productId,
                                               Integer locationId)
            throws NotFoundException;

    /**
     * <p>
     *     This method is used to decrease stock by
     *     user order details, and pinCode from where
     *     user has ordered the products
     *     if user placed order successfully
     * </p>
     * @param order it has product details, user ordered
     * @param pinCode It is user's location pinCode from where user ordered
     */
    void removeStockByOrderDetails(Order order,
                                   Integer pinCode);

    /**
     * <p>
     *     This method is used to update stock
     *     based on product details available in user order,
     *     if user cancelled the order
     * </p>
     * @param order order details which has product details
     *             that is cancelled
     */
    void updateStockByOrderDetails(Order order);

    /**
     * <p>
     *     This method used to check stock availability
     *     by store location and product id.
     * </p>
     * @param locationId to check if stock exist in given location
     * @param productId to check stock available for given product
     * @return true if stock exist for given product and location
     *          else false
     */
    Boolean getStocksAvailabilityByStoreLocationAndProduct(Integer locationId, Integer productId);
}
