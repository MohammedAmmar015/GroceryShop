package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;

import java.util.List;

/**
 * <p>
 *     Interface for Stock related Services
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
     * @return
     */
    SuccessDto addStock(StockRequestDto stockRequest, Integer locationId, Integer productId)
            throws NotFound, Existed;

    /**
     * <p>
     *     It is used to get Stock details for particular product,
     *     on different location
     * </p>
     * @param productId id to view Stock details
     * @return List of Stock details
     */
    List<StockResponseDto> getStockByProductId(Integer productId)
            throws NotFound;

    /**
     * <p>
     *     To view stock details, for particular product on particular location
     * </p>
     * @param productId - to view Stock details
     * @param locationId - to view stock by product and location
     * @return
     */
    StockResponseDto getStockByProductAndLocation(Integer productId, Integer locationId)
            throws NotFound;


    /**
     * <p>
     * To update stock for particular product on particular location
     * </p>
     *
     * @param stockRequest - stock details to update
     * @param productId    - id to update stock for this product
     * @param locationId   - id to update stock on this location
     * @return
     */
    SuccessDto updateStockByProductAndLocation(StockRequestDto stockRequest,
                                               Integer productId,
                                               Integer locationId)
            throws NotFound, NotFound;
}
