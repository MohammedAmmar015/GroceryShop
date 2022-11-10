package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.model.OrderDetails;
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
     * </p>
     * @param order - order details
     */
    public void removeStockByOrderDetails(UserOrder order, Integer pinCode) {
        Boolean isStoreAvailable = storeRepo.existsByPinCode(pinCode);
        if (!isStoreAvailable) {
            pinCode = storeRepo.findByIsActiveAndId(true, 1).getPinCode();
        }
        for (OrderDetails orderDetail : order.getOrderDetails()) {
            stockRepo.decreaseStockByProductsAndLocation(orderDetail.getQuantity(),
                                                        orderDetail.getProduct(),
                                                        pinCode);
        }
    }
}
