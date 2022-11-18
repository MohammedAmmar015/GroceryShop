/*
 * <p>
 *      Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.mapper.StockMapper;
import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.Stock;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.model.UserOrder;
import com.ideas2it.groceryshop.repository.StockRepo;
import com.ideas2it.groceryshop.service.ProductService;
import com.ideas2it.groceryshop.service.StockService;
import com.ideas2it.groceryshop.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Implementation class for Stock Service Interface
 *     This class implements methods which is used to add stock to
 *     to particular product on particular location,
 *     and to view stocks available of different products
 *     on different location
 * </p>
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final Logger logger = LogManager.getLogger(StockServiceImpl.class);
    private final StoreService storeService;
    private final StockRepo stockRepo;
    private final ProductHelper productHelper;

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto addStock(StockRequestDto stockRequest,
                                       Integer locationId,
                                       Integer productId)
                                       throws NotFoundException, ExistedException {
        logger.debug("Entered addStock method in StockServiceImpl");
        if (stockRepo.existsByStoreLocationIdAndProductId(locationId, productId)) {
            logger.error("stock already exist");
            throw new ExistedException("Stock already exists");
        }
        Stock stock = StockMapper.toStock(stockRequest);
        StoreLocation storeLocation = storeService.getStoreById(locationId);
        if (storeLocation == null) {
            logger.error("store not found");
            throw new NotFoundException("Store not found");
        }
        stock.setStoreLocation(storeLocation);
        Product product = productHelper.getProductById(productId);
        if (product == null) {
            logger.error("product not found");
            throw new NotFoundException("Product not found");
        }
        stock.setProduct(product);
        stock.setUnit(product.getUnit());
        stockRepo.save(stock);
        logger.debug("Stock created successfully");
        return new SuccessResponseDto(201,
                "Stock created successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StockResponseDto> getStockByProductId(Integer productId)
                                                      throws NotFoundException {
        logger.debug("Entered getStockByProductId method in StockServiceImpl");
        List<Stock> stocks = stockRepo.findByProductId(productId);
        if (stocks.isEmpty()) {
            logger.error("No stock data found");
            throw new NotFoundException("No data found");
        }
        List<StockResponseDto> stockResponse = new ArrayList<>();
        for (Stock stock : stocks) {
            stockResponse.add(StockMapper.toStockResponse(stock));
        }
        return stockResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StockResponseDto getStockByProductAndLocation(Integer productId,
                                                         Integer locationId)
                                                         throws NotFoundException {
        logger.debug("Entered getStockByProductAndLocation method in StockServiceImpl");
        Stock stock = stockRepo.findByProductIdAndStoreLocationId(productId, locationId);
        if (stock == null) {
            logger.error("stock not found");
            throw new NotFoundException("Stock not found");
        }
        return StockMapper.toStockResponse(stock);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto updateStockByProductAndLocation
                             (StockRequestDto stockRequest,
                             Integer productId,
                             Integer locationId)
                             throws NotFoundException {
        logger.debug("Entered updateStockByProductAndLocation method in StockServiceImpl");
        Integer rowsAffected
                = stockRepo.updateStockByProductAndLocation(stockRequest.getStock(),
                                                            productId,
                                                            locationId);
        if (rowsAffected == 0) {
            logger.error("product or location not found");
            throw new NotFoundException("Product or Location not found");
        }
        logger.debug("stock updated successfully");
        return new SuccessResponseDto(200, "Stock updated successfully");
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void removeStockByOrderDetails(UserOrder order,
                                          Integer pinCode) {
        logger.debug("Entered removeStockByOrderDetails method in StockServiceImpl");
        StoreLocation store = storeService.getStoreByPinCode(pinCode);
        for (OrderDetails orderDetail : order.getOrderDetails()) {
            stockRepo.decreaseStockByProductsAndLocation(orderDetail.getQuantity(),
                                                         orderDetail.getProduct(),
                                                         store.getId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStockByOrderDetails(UserOrder order) {
        logger.debug("Entered updateStockByOrderDetails method in StockServiceImpl");
        Integer pinCode = order.getOrderDelivery().getShippingAddress().getPinCode();
        StoreLocation store = storeService.getStoreByPinCode(pinCode);
        for (OrderDetails orderDetail : order.getOrderDetails()) {
            stockRepo.increaseStockByProductsAndLocation(orderDetail.getQuantity(),
                    orderDetail.getProduct(),
                    store.getId());
        }
    }

    /**
     * <p>
     *     This method used to check stock availability 
     *     by store location and product id.
     * </p>
     * @param locationId to check if stock exist in given location
     * @param productId to check stock available for given product
     * @return true if stock exist for given product and location
     *          else false
     */
    @Override
    public Boolean getStocksAvailabilityByStoreLocationAndProduct(Integer locationId, Integer productId) {
        return stockRepo.existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThan
                (locationId, productId, 0);
    }
}
