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
     * It is used to add Stock for Particular product based on Location
     * </p>
     *
     * @param stockRequest stock details to add
     * @param locationId   To add stock to particular location
     * @param productId    To add Stock to the Product
     * @return
     */
    @Override
    public SuccessDto addStock(StockRequestDto stockRequest,
                               Integer locationId,
                               Integer productId) throws NotFound, Existed {
        if (stockRepo.existsByStoreLocationIdOrProductId(locationId, productId)) {
            throw new Existed("Stock already exists for this location and product");
        }
        Stock stock = StockMapper.toStock(stockRequest);
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, locationId);
        if (storeLocation == null) {
            throw new NotFound("Store Location Not Found");
        }
        stock.setStoreLocation(storeLocation);
        Product product = productHelper.getProductById(productId);
        if (product == null) {
            throw new NotFound("Given product id Not Found");
        }
        stock.setProduct(product);
        stockRepo.save(stock);
        return new SuccessDto(201,
                "Stock created successfully for given product");
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

    public List<StockResponseDto> getStockByProductId(Integer productId) throws NotFound {
        List<Stock> stocks = stockRepo.findByProductId(productId);
        if (stocks.isEmpty()) {
            throw new NotFound("No Data Found");
        }
        List<StockResponseDto> stockResponse = new ArrayList<>();
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
    public StockResponseDto getStockByProductAndLocation(Integer productId, Integer locationId) throws NotFound {
        Stock stock = stockRepo.findByProductIdAndStoreLocationId(productId, locationId);
        if (stock == null) {
            throw new NotFound("Stock Not found for this Product and Location");
        }
        StockResponseDto stockResponse = StockMapper.toStockResponse(stock);
        return stockResponse;
    }

    /**
     * <p>
     * To Update Stock for particular product on different location
     * </p>
     *
     * @param stockRequest - stock details to update
     * @param productId    - id to update stock
     * @return
     */
    @Override
    public SuccessDto updateStockByProduct(StockRequestDto stockRequest,
                                           Integer productId) throws NotFound {
        Integer rowsAffected =
                stockRepo.updateStockByProduct(stockRequest.getAvailableStock(), productId);
        if (rowsAffected == 0) {
            throw new NotFound("Product Id Not Found");
        }
        return new SuccessDto(200, "Stock Updated successfully");
    }

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
    @Override
    public SuccessDto updateStockByProductAndLocation(StockRequestDto stockRequest,
                                                      Integer productId,
                                                      Integer locationId) throws NotFound {
        Integer rowsAffected = stockRepo.updateStockByProductAndLocation(stockRequest.getAvailableStock(),
                                                                    productId, locationId);
        if (rowsAffected == 0) {
            throw new NotFound("Product or Location Id Not Found");
        }
        return new SuccessDto(200, "Stock Updated Successfully");
    }
}
