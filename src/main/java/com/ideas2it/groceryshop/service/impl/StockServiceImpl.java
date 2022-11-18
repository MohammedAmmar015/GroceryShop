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
    private final ProductService productService;

    /**
     * <p>
     *     This method is used to add Stock for particular product
     *     based on Location id
     *     stockRequest has stock details to add stock for product 
     * </p>
     *
     * @param stockRequest stock details to add stock
     * @param locationId   To add stock to particular location
     * @param productId    To add Stock to the Product
     * @return SuccessResponseDto with success message and status code
     *          if stock created
     * @throws NotFoundException if store or product not found
     * @throws ExistedException if stock already exist for given product and location
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
        Product product = productService.getProductById(productId);
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
     * <p>
     *     This method is used to get available Stock details
     *     for particular product,
     *     available on different location
     *     It will return Stock details as list Response DTO
     * </p>
     * @param productId id to view Stock details of product
     * @return List of Stock details on different location
     * @throws NotFoundException if stock not found for given product id
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
     * <p>
     *     This method is used to view stock details,
     *     for particular product on particular location
     *     based on product id and location id
     *     It will return available stock details
     *     as response DTO
     * </p>
     * @param productId - to view Stock details of product
     * @param locationId - to view stock by location
     * @return Store Response DTO with available stock
     * @throws NotFoundException if stock not found for given id
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
     * <p>
     *     This method is used to update stock
     *     for particular product on particular location
     *     based on product id and location id
     *     stockRequest has stock details to be added
     *     to stock
     * </p>
     *
     * @param stockRequest - stock details to update
     * @param productId    - id to update stock for this product
     * @param locationId   - id to update stock on this location
     * @return SuccessResponseDto success message with status code
     *          if Stock updated
     * @throws NotFoundException if stock not found for product id and location id
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
     * <p>
     *     This method is used to decrease stock by
     *     user order details, and pinCode from where
     *     user has ordered the products
     *     if user placed order successfully
     * </p>
     * @param order it has product details, user ordered
     * @param pinCode It is user's location pinCode from where user ordered
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
     * <p>
     *     This method is used to update stock
     *     based on product details available in user order,
     *     if user cancelled the order
     * </p>
     * @param order order details which has product details
     *             that is cancelled
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
