package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.mapper.StockMapper;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.Stock;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.repository.StockRepo;
import com.ideas2it.groceryshop.repository.StoreRepo;
import com.ideas2it.groceryshop.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Implementation class for Stock Service
 *     This class is used to do add, update,
 *     get stock details for different product and location
 * </p>
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {

    private StoreRepo storeRepo;
    private StockRepo stockRepo;
    private ProductHelper productHelper;


    /**
     * <p>
     *     This method is used to add Stock for particular product
     *     based on Location
     * </p>
     *
     * @param stockRequest stock details to add
     * @param locationId   To add stock to particular location
     * @param productId    To add Stock to the Product
     * @return successDto if stock created
     * @throws NotFound if store or product not found
     * @throws Existed if stock already exist for given product and location
     */
    @Override
    public SuccessDto addStock(StockRequestDto stockRequest,
                               Integer locationId,
                               Integer productId) throws NotFound, Existed {
        if (stockRepo.existsByStoreLocationIdAndProductId(locationId, productId)) {
            throw new Existed("Stock already exists");
        }
        Stock stock = StockMapper.toStock(stockRequest);
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, locationId);
        if (storeLocation == null) {
            throw new NotFound("Store not found");
        }
        stock.setStoreLocation(storeLocation);
        Product product = productHelper.getProductById(productId);
        if (product == null) {
            throw new NotFound("Product not found");
        }
        stock.setProduct(product);
        stock.setUnit(product.getUnit().substring(2));
        stockRepo.save(stock);
        return new SuccessDto(201,
                "Stock created successfully");
    }

    /**
     * <p>
     *     This method is used to get Stock details for particular product,
     *     available on different location
     * </p>
     * @param productId id to view Stock details
     * @return List of Stock details
     * @throws NotFound if stock not found for given product id
     */
    @Override
    public List<StockResponseDto> getStockByProductId(Integer productId) throws NotFound {
        List<Stock> stocks = stockRepo.findByProductId(productId);
        if (stocks.isEmpty()) {
            throw new NotFound("No data found");
        }
        List<StockResponseDto> stockResponse = new ArrayList<>();
        for (Stock stock : stocks) {
            stockResponse.add(StockMapper.toStockResponse(stock));
        }
        return stockResponse;
    }

    /**
     * <p>
     *     This method is used to view stock details,
     *     for particular product on particular location
     *     based on product id and location id
     * </p>
     * @param productId - to view Stock details
     * @param locationId - to view stock by product and location
     * @return Store Response DTO
     * @throws NotFound if stock not found for given ids
     */
    @Override
    public StockResponseDto getStockByProductAndLocation(Integer productId, Integer locationId) throws NotFound {
        Stock stock = stockRepo.findByProductIdAndStoreLocationId(productId, locationId);
        if (stock == null) {
            throw new NotFound("Stock not found");
        }
        StockResponseDto stockResponse = StockMapper.toStockResponse(stock);
        return stockResponse;
    }

    /**
     * <p>
     *     This method is used to update stock
     *     for particular product on particular location
     *     based on product id and location id
     * </p>
     *
     * @param stockRequest - stock details to update
     * @param productId    - id to update stock for this product
     * @param locationId   - id to update stock on this location
     * @return SuccessDto if Stock updated
     * @throws NotFound if stock not found for product id and location id
     */
    @Override
    public SuccessDto updateStockByProductAndLocation(StockRequestDto stockRequest,
                                                      Integer productId,
                                                      Integer locationId) throws NotFound {
        Integer rowsAffected = stockRepo.updateStockByProductAndLocation(stockRequest.getStock(),
                                                                    productId, locationId);
        if (rowsAffected == 0) {
            throw new NotFound("Product or Location not found");
        }
        return new SuccessDto(200, "Stock updated successfully");
    }
}
