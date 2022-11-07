package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.UserOrder;
import com.ideas2it.groceryshop.repository.StockRepo;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class StockHelper {
    private StockRepo stockRepo;

    public void decreaseStockByOrderDetails(UserOrder order) {
        Integer locationId = 1;
        for (OrderDetails orderDetail : order.getOrderDetails()) {
            stockRepo.decreaseStockByProductsAndLocation(orderDetail.getQuantity(), orderDetail.getProduct(), locationId);
        }

    }
}
