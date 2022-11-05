package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.StockRequest;
import com.ideas2it.groceryshop.mapper.StockMapper;
import com.ideas2it.groceryshop.model.Stock;
import com.ideas2it.groceryshop.service.StockService;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {
    @Override
    public void addStock(StockRequest stockRequest, Integer locationId) {
        /*Stock stock = StockMapper.toStock(stockRequest);
        stock.setStoreLocation();*/
    }
}
