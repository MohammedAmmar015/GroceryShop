package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.model.UserOrder;
import com.ideas2it.groceryshop.repository.StockRepo;
import com.ideas2it.groceryshop.repository.StoreRepo;
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
    private StockRepo stockRepo;
    private StoreRepo storeRepo;

    /**
     * <p>
     *     To remove Stock for products, when user Ordered
     *     based on order details
     * </p>
     * @param order - order details
     * @throws NotFound if location not found
     */
    public void removeStockByOrderDetails(UserOrder order, Integer pinCode) throws NotFound {
        StoreLocation store = storeRepo.findByIsActiveAndPinCode(true, pinCode);
        if (store == null) {
            throw new NotFound("Stock not available Try any other location");
        }
        for (OrderDetails orderDetail : order.getOrderDetails()) {
            stockRepo.decreaseStockByProductsAndLocation(orderDetail.getQuantity(),
                                                        orderDetail.getProduct(),
                                                        store.getId());
        }
    }
}
