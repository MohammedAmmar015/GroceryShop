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
<<<<<<< Updated upstream
    SuccessDto addStock(StockRequest stockRequest, Integer locationId, Integer productId)
            throws NotFound, Existed;
=======
    SuccessDto addStock(StockRequestDto stockRequest, Integer locationId, Integer productId)
            throws NotFoundException, Existed;
>>>>>>> Stashed changes

    /**
     * <p>
     *     It is used to get Stock details for particular product,
     *     on different location
     * </p>
     * @param productId id to view Stock details
     * @return List of Stock details
     */
<<<<<<< Updated upstream
    List<StockResponse> getStockByProductId(Integer productId)
            throws NotFound, NotFound;
=======
    List<StockResponseDto> getStockByProductId(Integer productId)
            throws NotFoundException;
>>>>>>> Stashed changes

    /**
     * <p>
     *     To view stock details, for particular product on particular location
     * </p>
     * @param productId - to view Stock details
     * @param locationId - to view stock by product and location
     * @return
     */
<<<<<<< Updated upstream
    StockResponse getStockByProductAndLocation(Integer productId, Integer locationId)
            throws NotFound, NotFound;
=======
    StockResponseDto getStockByProductAndLocation(Integer productId, Integer locationId)
            throws NotFoundException;
>>>>>>> Stashed changes

    /**
     * <p>
     * To Update Stock for particular product on different location
     * </p>
     *
     * @param stockRequest - stock details to update
     * @param productId    - id to update stock
     * @return
     */
<<<<<<< Updated upstream
    SuccessDto updateStockByProduct(StockRequest stockRequest, Integer productId)
            throws NotFound, NotFound;
=======
    SuccessDto updateStockByProduct(StockRequestDto stockRequest, Integer productId)
            throws NotFoundException;
>>>>>>> Stashed changes


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
