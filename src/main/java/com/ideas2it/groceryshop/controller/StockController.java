package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StockRequest;
import com.ideas2it.groceryshop.dto.StockResponse;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * <p>
 *     This is Stock Controller to do Stock related operation
 * </p>
 * @author Mohammed Ammar
 * @since 04-11-2022
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    private StockService stockService;

    /**
     * <p>
     *     This API is used to create stock for particular Product on particular location
     * </p>
     * @param stockRequest - stock details to create stock
     * @param locationId - to create stock for particular location
     * @param productId - to create stock for particular product
     */
    @PostMapping("/{locationId}/{productId}")
    public SuccessDto createStock(@RequestBody StockRequest stockRequest,
                            @PathVariable("locationId") Integer locationId,
                            @PathVariable("productId") Integer productId)
            throws Existed, NotFoundException {

        return stockService.addStock(stockRequest, locationId, productId);
    }

    /**
     * <p>
     *     This API is used to view Stock details for Particular product on different location
     * </p>
     * @param productId - to view stock for this product on different location
     * @return - list of Stock response
     */
    @GetMapping("/{productId}")
    public List<StockResponse> viewStockByProduct(@PathVariable Integer productId)
            throws NotFoundException {
        return stockService.getStockByProductId(productId);
    }

    /**
     * <p>
     *     This API is used to view Stock for particular product on particular location
     * </p>
     * @param productId - to view stock of this product
     * @param locationId - to view stock in this location
     * @return
     */
    @GetMapping("/{productId}/{locationId}")
    public StockResponse getStockByProductAndLocation(@PathVariable Integer productId,
                                                      @PathVariable Integer locationId)
            throws NotFoundException {
        return stockService.getStockByProductAndLocation(productId,locationId);
    }

    /**
     * <p>
     *     This API is used to Update Stock for Particular Product on different location
     * </p>
     * @param stockRequest - stock details to be updated
     * @param productId - to update stock for this product
     */
    @PutMapping("/{productId}")
    public SuccessDto updateStockByProduct(@RequestBody StockRequest stockRequest,
                                           @PathVariable Integer productId) throws NotFoundException {
        return stockService.updateStockByProduct(stockRequest, productId);
    }

    /**
     * <p>
     *     This API is used to Update stock for Particular product,
     *     on particular location
     * </p>
     * @param stockRequest - stock details to be updated
     * @param productId - to update stock for this product
     * @param locationId - to update stock on this location
     */
    @PutMapping("/{productId}/{locationId}")
    public SuccessDto updateStockByProductAndLocation(@RequestBody StockRequest stockRequest,
                                                @PathVariable Integer productId,
                                                @PathVariable Integer locationId) throws NotFoundException {
        return stockService.updateStockByProductAndLocation(stockRequest, productId, locationId);
    }
}
