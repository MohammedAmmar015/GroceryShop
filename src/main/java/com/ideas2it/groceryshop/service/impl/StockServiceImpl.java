/*
 * <p>
 *      Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.mapper.StockMapper;
import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.Stock;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.model.UserOrder;
import com.ideas2it.groceryshop.repository.StockRepo;
import com.ideas2it.groceryshop.repository.StoreRepo;
import com.ideas2it.groceryshop.service.StockService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class StockServiceImpl implements StockService {

    private final Logger logger;
    private final StoreRepo storeRepo;
    private final StockRepo stockRepo;
    private final ProductHelper productHelper;

    public StockServiceImpl(StoreRepo storeRepo,
                            StockRepo stockRepo,
                            ProductHelper productHelper) {
        this.logger = LogManager.getLogger(StockServiceImpl.class);
        this.storeRepo = storeRepo;
        this.stockRepo = stockRepo;
        this.productHelper = productHelper;
    }

    /**
     * <p>
     *     This method is used to add Stock for particular product
     *     based on Location
     * </p>
     *
     * @param stockRequest stock details to add
     * @param locationId   To add stock to particular location
     * @param productId    To add Stock to the Product
     * @return SuccessResponseDto if stock created
     * @throws NotFound if store or product not found
     * @throws Existed if stock already exist for given product and location
     */
    @Override
    public SuccessResponseDto addStock(StockRequestDto stockRequest,
                               Integer locationId,
                               Integer productId) throws NotFound, Existed {
        logger.debug("Entered addStock method in StockServiceImpl");
        if (stockRepo.existsByStoreLocationIdAndProductId(locationId, productId)) {
            logger.error("stock already exist");
            throw new Existed("Stock already exists");
        }
        Stock stock = StockMapper.toStock(stockRequest);
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, locationId);
        if (storeLocation == null) {
            logger.error("store not found");
            throw new NotFound("Store not found");
        }
        stock.setStoreLocation(storeLocation);
        Product product = productHelper.getProductById(productId);
        if (product == null) {
            logger.error("product not found");
            throw new NotFound("Product not found");
        }
        stock.setProduct(product);
        stock.setUnit(product.getUnit());
        stockRepo.save(stock);
        logger.debug("stock created successfully");
        return new SuccessResponseDto(201,
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
        logger.debug("Entered getStockByProductId method in StockServiceImpl");
        List<Stock> stocks = stockRepo.findByProductId(productId);
        if (stocks.isEmpty()) {
            logger.error("No stock data found");
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
    public StockResponseDto getStockByProductAndLocation(Integer productId,
                                                         Integer locationId) throws NotFound {
        logger.debug("Entered getStockByProductAndLocation method in StockServiceImpl");
        Stock stock = stockRepo.findByProductIdAndStoreLocationId(productId, locationId);
        if (stock == null) {
            logger.error("stock not found");
            throw new NotFound("Stock not found");
        }
        return StockMapper.toStockResponse(stock);
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
     * @return SuccessResponseDto if Stock updated
     * @throws NotFound if stock not found for product id and location id
     */
    @Override
    public SuccessResponseDto updateStockByProductAndLocation(StockRequestDto stockRequest,
                                                      Integer productId,
                                                      Integer locationId) throws NotFound {
        logger.debug("Entered updateStockByProductAndLocation method in StockServiceImpl");
        Integer rowsAffected = stockRepo.updateStockByProductAndLocation(stockRequest.getStock(),
                                                                    productId, locationId);
        if (rowsAffected == 0) {
            logger.error("product or location not found");
            throw new NotFound("Product or Location not found");
        }
        logger.debug("stock updated successfully");
        return new SuccessResponseDto(200, "Stock updated successfully");
    }

    /**
     * <p>
     *     This method is used to decrease stock by
     *     user order details, and location details
     * </p>
     * @param order it has product details, user ordered
     * @param store store location that user ordered for
     */
    @Override
    public void removeStockByOrderDetails(UserOrder order, StoreLocation store) {
        logger.debug("Entered removeStockByOrderDetails method in StockServiceImpl");
        for (OrderDetails orderDetail : order.getOrderDetails()) {
            stockRepo.decreaseStockByProductsAndLocation(orderDetail.getQuantity(),
                    orderDetail.getProduct(),
                    store.getId());
        }
    }

    @Override
    public void updateStockByOrderDetails(UserOrder order, StoreLocation store) {
        logger.debug("Entered updateStockByOrderDetails method in StockServiceImpl");
        for (OrderDetails orderDetail : order.getOrderDetails()) {
            stockRepo.increaseStockByProductsAndLocation(orderDetail.getQuantity(),
                    orderDetail.getProduct(),
                    store.getId());
        }
    }
}
