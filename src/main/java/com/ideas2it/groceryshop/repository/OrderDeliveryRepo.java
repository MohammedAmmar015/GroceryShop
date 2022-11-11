package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.OrderDelivery;
import com.ideas2it.groceryshop.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderDeliveryRepo extends JpaRepository<OrderDelivery, Integer> {

    /**
     * <p>
     *     This method is used to retrieve order using orderId
     * </p>
     * @param OrderId
     * @return OrderDelivery
     */
    OrderDelivery findByUserOrderId(Integer OrderId);

    /**
     * <p>
     *     This method is used to make change the delivery status
     * </p>
     * @param orderId
     * @return
     */
    @Modifying
    @Transactional
    @Query("Update OrderDelivery od set od.isDelivered = true where od.id = (select o.orderDelivery.id from UserOrder o where o.id = ?1)")
    Integer updateStatus(Integer orderId);

}
