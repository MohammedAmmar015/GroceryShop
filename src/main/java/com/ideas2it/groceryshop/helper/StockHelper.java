/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.model.UserOrder;
import com.ideas2it.groceryshop.repository.StoreRepo;
import com.ideas2it.groceryshop.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 *     This is Stock Helper class to add or remove Stock when order is placed
 * </p>
 * @author Mohammed Ammar
 * @since 07-11-2022
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class StockHelper {
    private StoreRepo storeRepo;
    private StockService stockService;

    /**
     * <p>
     *     This method is used to remove Stock for products,
     *     that user has Ordered
     *     based on order details
     *     and store location that user selected for
     * </p>
     * @param order - order details
     * @throws NotFound if location not found
     */
    public void removeStockByOrderDetails(UserOrder order, Integer pinCode) throws NotFound {
        StoreLocation store = storeRepo.findByIsActiveAndPinCode(true, pinCode);
        if (store == null) {
            throw new NotFound("Stock not available Try any other location");
        }
        stockService.removeStockByOrderDetails(order, store);
    }

    /**
     * <p>
     *     This method is used to update stock
     *     when user cancel order
     * </p>
     * @param order user order details that is cancelled
     */
    public void updateStockByOrderDetails(UserOrder order) {
        Integer pinCode = order.getOrderDelivery().getShippingAddress().getPinCode();
        StoreLocation store = storeRepo.findByIsActiveAndPinCode(true, pinCode);
        stockService.updateStockByOrderDetails(order, store);
    }
}
