package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.dto.UserOrderResponseDto;
import com.ideas2it.groceryshop.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface UserOrderRepo extends JpaRepository<UserOrder, Integer> {

    /**
     * <p>
     *     This method is used to retrieve all the active orders
     * </p>
     * @param status
     * @return List<UserOrder>
     */
    List<UserOrder> findByIsActive(Boolean status);

    /**
     * <p>
     *     This method is used to retrieve order using userId
     * </p>
     * @param userId
     * @return List<UserOrder>
     */
    List<UserOrder> findByUserId(Integer userId);

    /**
     * <p>
     *     This method is used to make a soft delete
     * </p>
     * @param order_id
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "update UserOrder set isActive = false where id = ?1")
    Integer cancelOrderbyId(Integer order_id);

    /**
     * <p>
     *     This method is used to retrieve order using productId
     * </p>
     * @param productId
     * @return List<UserOrder>
     */
    @Modifying
    @Transactional
    @Query(value = "Select * from user_order inner join order_details " +
            "on order_details.order_id = user_order.id " +
            "where order_details.product_id = :productId", nativeQuery = true)
    List<UserOrder> findByProductId(Integer productId);

    /**
     * This method is used to retrieve user order by ordered date
     * @param orderedDate
     * @return List<UserOrder>
     */
    @Query("Select o from UserOrder o where date(o.orderedDate) = ?1")
    List<UserOrder> findByOrderedDate(Date orderedDate);

    /**
     * This method is used to retrieve user order by using userId and orderedDate
     * @param orderedDate
     * @param userId
     * @return List<UserOrder>
     */
    @Query("Select o from UserOrder o where Date(o.orderedDate) = ?1 AND o.user.id = ?2")
    List<UserOrder> findByOrderedDateAndUserId(Date orderedDate, Integer userId);

}
