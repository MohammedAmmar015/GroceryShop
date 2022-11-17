/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.StockService;
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
 *     This is Stock Rest Controller to do Stock related operation
 *     1. To create stock
 *     2. To view stock by product
 *     3. To view stock by product and location
 *     4. To update stock by product and location
 * </p>
 * @author Mohammed Ammar
 * @since 04-11-2022
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    private final Logger logger;
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.logger = LogManager.getLogger(StockController.class);
        this.stockService = stockService;
    }

    /**
     * <p>
     *     This POST API api/v1/stocks/{locationId}/{productId}
     *     is used to create stock for particular Product on particular location
     * </p>
     * @param stockRequest - stock details to create stock
     * @param locationId - to create stock for particular location
     * @param productId - to create stock for particular product
     * @return SuccessResponseDto if stock created successfully
     * @throws NotFound if product or location not found
     * @throws Existed if stock already exist for given product and location
     */
    @PostMapping("/{locationId}/{productId}")
    public SuccessResponseDto createStock(@Valid @RequestBody StockRequestDto stockRequest,
                                          @PathVariable("locationId") Integer locationId,
                                          @PathVariable("productId") Integer productId)
                                          throws Existed, NotFound {
        logger.debug("Entered createStock method in StockController");
        return stockService.addStock(stockRequest, locationId, productId);
    }

    /**
     * <p>
     *     This GET API api/v1/stocks/{productId}
     *     is used to view Stock details for Particular product
     *     on different location
     * </p>
     * @param productId - to view stock for this product on different location
     * @return - list of Stock response
     * @throws NotFound if Stock not found for given product id
     */
    @GetMapping("/{productId}")
    public List<StockResponseDto> viewStockByProduct(@PathVariable Integer productId)
                                                     throws NotFound {
        logger.debug("Entered viewStockByProduct method in StockController");
        return stockService.getStockByProductId(productId);
    }

    /**
     * <p>
     *     This GET API api/v1/stocks/{locationId}/{productId}
     *     is used to view Stock for particular product
     *     on particular location
     * </p>
     * @param productId - to view stock of this product
     * @param locationId - to view stock in this location
     * @return stock response DTO
     * @throws NotFound if stock not found for given location and product
     */
    @GetMapping("/{locationId}/{productId}")
    public StockResponseDto getStockByProductAndLocation(@PathVariable Integer productId,
                                                         @PathVariable Integer locationId)
                                                         throws NotFound {
        logger.debug("Entered getStockByProductAndLocation method in StockController");
        return stockService.getStockByProductAndLocation(productId,locationId);
    }

    /**
     * <p>
     *     This PUT API api/v1/stocks/{locationId}/{productId}
     *     is used to Update stock for Particular product,
     *     on particular location
     * </p>
     * @param stockRequest - stock details to be updated
     * @param productId - to update stock for this product
     * @param locationId - to update stock on this location
     * @return SuccessResponseDto if stock updated successfully
     * @throws NotFound if product or location not found
     */
    @PutMapping("/{locationId}/{productId}")
    public SuccessResponseDto updateStockByProductAndLocation
                            (@Valid @RequestBody StockRequestDto stockRequest,
                             @PathVariable Integer productId,
                             @PathVariable Integer locationId) throws NotFound {
        logger.debug("Entered updateStockByProductAndLocation method in StockController");
        return stockService.updateStockByProductAndLocation(stockRequest,
                                                            productId,
                                                            locationId);
    }
}
