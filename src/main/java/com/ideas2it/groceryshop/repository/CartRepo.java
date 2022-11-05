package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

    Cart findByIdAndIsActive(Integer cartId);
    Cart findByUserIdAndIsActive(Integer userId, Boolean is_active);

    @Modifying(clearAutomatically = true)
    @Query("Update Cart c SET isActive = true where c.user.id = ?1")
    void deleteCartByUserId(Integer userId);
}
