package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface UserOrderRepo extends JpaRepository<UserOrder, Integer> {
    List<UserOrder> findByIsActive(Boolean status);
    List<UserOrder> findByUserId(Integer userId);

    @Modifying
    @Transactional
    @Query(value = "update UserOrder set isActive = false where id = ?1")
    Integer cancelOrderbyId(Integer order_id);

    @Modifying
    @Transactional
    @Query(value = "Select * from user_order inner join order_details on order_details.order_id = user_order.id where order_details.product_id = :productId", nativeQuery = true)
    List<UserOrder> findByProductId(Integer productId);

    @Query(value = "Select * from user_order where user_order = :orderedDate", nativeQuery = true)
    List<UserOrder> findByOrderedDate(Date orderedDate);

    //UserOrder findByIdAndIsActive(Boolean isActive, int cartId);

}
