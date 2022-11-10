package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.OrderDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDeliveryRepo extends JpaRepository<OrderDelivery, Integer> {

    /**
     * <p>
     *     This method is used to retrieve order using orderId
     * </p>
     * @param OrderId
     * @return OrderDelivery
     */
    OrderDelivery findByUserOrderId(Integer OrderId);

}
