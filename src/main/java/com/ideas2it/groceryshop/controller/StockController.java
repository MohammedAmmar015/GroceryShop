/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.StockService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *     Provides APIs to add, update, view and delete stock
 *     for different products on different location
 * </p>
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    private final Logger logger = LogManager.getLogger(StockController.class);
    private final StockService stockService;

    /**
     * <p>
     *     Create Stock for particular product on particular location
     *     by getting stockRequest from user to add
     * </p>
     * @param stockRequest - contains number of stock to add
     * @param locationId - to add stock on given location
     * @param productId - to add stock for given product
     * @return SuccessResponseDto - contains success message and status code
     * @throws NotFoundException - if given store id or product id not found
     * @throws ExistedException - if stock already exist for given product and location
     */
    @PostMapping("/location/{locationId}/products/{productId}")
    public SuccessResponseDto createStock(@Valid @RequestBody StockRequestDto stockRequest,
                                          @PathVariable("locationId") Integer locationId,
                                          @PathVariable("productId") Integer productId)
                                          throws ExistedException, NotFoundException {
        logger.debug("Entered createStock method in StockController");
        return stockService.addStock(stockRequest, locationId, productId);
    }

    /**
     * <p>
     *     View available Stock details for particular product
     *     available on different locations by getting product id from user
     * </p>
     * @param productId - to view stock details on different location
     * @return list of StockResponseDto - contains list of available stock for multiple location
     *                                      for given product id
     * @throws NotFoundException - if stock not found for given product id
     */
    @GetMapping("/products/{productId}")
    public List<StockResponseDto> viewStockByProduct(@PathVariable Integer productId)
                                                     throws NotFoundException {
        logger.debug("Entered viewStockByProduct method in StockController");
        return stockService.getStockByProductId(productId);
    }

    /**
     * <p>
     *     View available stock detail for particular product
     *     on particular location based on product id and location id
     *     by getting product id and location id
     * </p>
     * @param productId - to view stock detail for given product
     * @param locationId - to view stock detail on given
     * @return StoreResponseDTO - contains available stock detail for given product and location
     * @throws NotFoundException - if stock not found for given id
     */
    @GetMapping("/location/{locationId}/products/{productId}")
    public StockResponseDto getStockByProductAndLocation(@PathVariable Integer productId,
                                                         @PathVariable Integer locationId)
                                                         throws NotFoundException {
        logger.debug("Entered getStockByProductAndLocation method in StockController");
        return stockService.getStockByProductAndLocation(productId,locationId);
    }

    /**
     * <p>
     *     Update stock for particular product on particular location
     *     based on product id and location id
     *     by getting locationId and productId from user
     * </p>
     *
     * @param stockRequest - stock details to update
     * @param productId    - to update stock for this product
     * @param locationId   -  to update stock on this location
     * @return SuccessResponseDto - contains success message and status code
     * @throws NotFoundException - if stock not found for product id and location id
     */
    @PutMapping("location/{locationId}/products/{productId}")
    public SuccessResponseDto updateStockByProductAndLocation
                            (@Valid @RequestBody StockRequestDto stockRequest,
                             @PathVariable Integer productId,
                             @PathVariable Integer locationId) throws NotFoundException {
        logger.debug("Entered updateStockByProductAndLocation method in StockController");
        return stockService.updateStockByProductAndLocation(stockRequest,
                                                            productId,
                                                             locationId);
    }
}
