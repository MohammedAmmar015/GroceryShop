package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.UserOrder;
import com.ideas2it.groceryshop.repository.StockRepo;
import lombok.AllArgsConstructor;

/**
 * <p>
 *     This is Stock Helper class to add or remove Stock when order is placed
 * </p>
 * @author Mohammed Ammar
 * @since 07-11-2022
 * @version 1.0
 */
@AllArgsConstructor
public class StockHelper {
    private StockRepo stockRepo;

    /**
     * <p>
     *     To remove Stock for products, when user Ordered
     * </p>
     * @param order - order details
     */
    public void removeStockByOrderDetails(UserOrder order) {
        Integer locationId = 1;
        for (OrderDetails orderDetail : order.getOrderDetails()) {
            stockRepo.decreaseStockByProductsAndLocation(orderDetail.getQuantity(), orderDetail.getProduct(), locationId);
        }
    }
}
