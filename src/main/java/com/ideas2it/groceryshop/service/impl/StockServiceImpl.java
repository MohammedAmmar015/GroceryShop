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

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {

    private StoreRepo storeRepo;
    private StockRepo stockRepo;
    private ProductHelper productHelper;


    @Override
    public void addStock(StockRequest stockRequest, Integer locationId, Integer productId) {
        Stock stock = StockMapper.toStock(stockRequest);
        StoreLocation storeLocation = storeRepo.findByIsActiveAndId(true, locationId);
        Product product = productHelper.getProductById(productId);
        stock.setStoreLocation(storeLocation);
        stock.setProduct(product);
        stockRepo.save(stock);
    }

    @Override
    public List<StockResponse> getStockByProductId(Integer productId) {
        List<Stock> stocks = stockRepo.findByProductId(productId);
        List<StockResponse> stockResponse = new ArrayList<>();
        for (Stock stock : stocks) {
            stockResponse.add(StockMapper.toStockResponse(stock));
        }
        return stockResponse;
    }

    @Override
    public List<StockResponse> getStockByProductAndLocation(Integer productId, Integer locationId) {
        List<Stock> stocks = stockRepo.findByProductIdAndStoreLocationId(productId, locationId);
        List<StockResponse> stockResponse = new ArrayList<>();
        for (Stock stock : stocks) {
            stockResponse.add(StockMapper.toStockResponse(stock));
        }
        return stockResponse;
    }

    @Override
    public void updateStockByProduct(StockRequest stockRequest, Integer productId) {
        stockRepo.updateStockByProduct(stockRequest.getAvailableStock(), productId);
    }

    @Override
    public void updateStockByProductAndLocation(StockRequest stockRequest,
                                                Integer productId,
                                                Integer locationId) {
        stockRepo.updateStockByProductAndLocation(stockRequest.getAvailableStock(), productId, locationId);
    }
}
