package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface UserOrderRepo extends JpaRepository<UserOrder, Integer> {

    /**
     * This method is used to retrieve all the active orders
     *
     * @param status it contains true
     * @return List<UserOrder> orderedDate, totalPrice,
     * totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    List<UserOrder> findByIsActive(Boolean status);
    /**
     * This method is used to retrieve order using userId
     *
     * @param userId It contains user id
     * @return List<UserOrder> orderedDate, totalPrice,
     * totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    List<UserOrder> findByUserId(Integer userId);

    /**
     * This method is used to make a soft delete
     *
     * @param orderId It contains order id
     * @return 0 or 1
     */
    @Modifying
    @Transactional
    @Query(value = "update UserOrder set isActive = false where id = ?1")
    Integer cancelOrderbyId(Integer orderId);

    /**
     * This method is used to retrieve order using productId
     *
     * @param productId It contains product id
     * @return List<OrderDetails> It contains quantity, price, product
     */
    @Query(value = "Select o from OrderDetails o where o.product.id = ?1")
    List<OrderDetails> findByProductId(Integer productId);

    /**
     * This method is used to retrieve user order by ordered date
     *
     * @param orderedDate It contains order date
     * @return List<UserOrder> orderedDate, totalPrice,
     * totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    @Query("Select o from UserOrder o where date(o.orderedDate) = ?1")
    List<UserOrder> findByOrderedDate(Date orderedDate);

    /**
     * This method is used to retrieve user order by using userId and orderedDate
     *
     * @param orderedDate It contains order date
     * @param userId It contains user id
     * @return List<UserOrder> orderedDate, totalPrice,
     * totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    @Query("Select o from UserOrder o where Date(o.orderedDate) = ?1 AND o.user.id = ?2")
    List<UserOrder> findByOrderedDateAndUserId(Date orderedDate, Integer userId);

}
