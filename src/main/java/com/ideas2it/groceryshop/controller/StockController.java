package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;
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
    public SuccessDto createStock(@Valid @RequestBody StockRequestDto stockRequest,
                            @PathVariable("locationId") Integer locationId,
                            @PathVariable("productId") Integer productId)
            throws Existed, NotFound {

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
    public List<StockResponseDto> viewStockByProduct(@PathVariable Integer productId)
            throws NotFound {
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
    @GetMapping("/{locationId}/{productId}")
    public StockResponseDto getStockByProductAndLocation(@PathVariable Integer productId,
                                                         @PathVariable Integer locationId)
            throws NotFound {
        return stockService.getStockByProductAndLocation(productId,locationId);
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
    @PutMapping("/{locationId}/{productId}")
    public SuccessDto updateStockByProductAndLocation(@Valid @RequestBody StockRequestDto stockRequest,
                                                @PathVariable Integer productId,
                                                @PathVariable Integer locationId) throws NotFound {
        return stockService.updateStockByProductAndLocation(stockRequest, productId, locationId);
    }
}
