package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.StockRequest;
import com.ideas2it.groceryshop.dto.StockResponse;
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
     *     It is used to add Stock for Particular product based on Location
     * </p>
     * @param stockRequest stock details to add
     * @param locationId To add stock to particular location
     * @param productId To add Stock to the Product
     */
    @Override
    public void addStock(StockRequest stockRequest, Integer locationId, Integer productId) {
        Stock stock = StockMapper.toStock(stockRequest);
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, locationId);
        Product product = productHelper.getProductById(productId);
        stock.setStoreLocation(storeLocation);
        stock.setProduct(product);
        stockRepo.save(stock);
    }

    /**
     * <p>
     *     It is used to get Stock details for particular product,
     *     on different location
     * </p>
     * @param productId id to view Stock details
     * @return List of Stock details
     */
    @Override
    public List<StockResponse> getStockByProductId(Integer productId) {
        List<Stock> stocks = stockRepo.findByProductId(productId);
        List<StockResponse> stockResponse = new ArrayList<>();
        for (Stock stock : stocks) {
            stockResponse.add(StockMapper.toStockResponse(stock));
        }
        return stockResponse;
    }

    /**
     * <p>
     *     To view stock details, for particular product on particular location
     * </p>
     * @param productId - to view Stock details
     * @param locationId - to view stock by product and location
     * @return
     */
    @Override
    public StockResponse getStockByProductAndLocation(Integer productId, Integer locationId) {
        Stock stock = stockRepo.findByProductIdAndStoreLocationId(productId, locationId);
        StockResponse stockResponse = StockMapper.toStockResponse(stock);
        return stockResponse;
    }

    /**
     * <p>
     *     To Update Stock for particular product on different location
     * </p>
     * @param stockRequest - stock details to update
     * @param productId - id to update stock
     */
    @Override
    public void updateStockByProduct(StockRequest stockRequest, Integer productId) {
        stockRepo.updateStockByProduct(stockRequest.getAvailableStock(), productId);
    }

    /**
     * <p>
     *     To update stock for particular product on particular location
     * </p>
     * @param stockRequest - stock details to update
     * @param productId - id to update stock for this product
     * @param locationId - id to update stock on this location
     */
    @Override
    public void updateStockByProductAndLocation(StockRequest stockRequest,
                                                Integer productId,
                                                Integer locationId) {
        stockRepo.updateStockByProductAndLocation(stockRequest.getAvailableStock(), productId, locationId);
    }
}
