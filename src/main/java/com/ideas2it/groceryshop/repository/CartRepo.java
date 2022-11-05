package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * <p>
 *     This is Cart Repository to do CRUD related to CART
 * </p>
 * @author Mohammed Ammar
 * @since 03-11-2022
 * @version 1.0
 */
@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

    Cart findByUserIdAndIsActive(Integer userId, Boolean isActive);

    Cart findByIdAndIsActive(Integer cartId, Boolean isActive);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("Update Cart c SET c.isActive = false where c.user = ?1")
    //@Query(value = "update cart_details set is_active = 0 where cart_id = (select id from cart where user_id = ?1)", nativeQuery = true)
    void deleteCartByUserId(User user);

    Cart findByUserAndIsActive(User user, Boolean isActive);
}
