package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * <p>
 *     This is CartDetailsRepository which extends JpaRepository,
 *     by default we can use default methods in JpaRepository,
 *     We can also add our custom methods in this repository to do
 *     database operations on cartDetails Entity
 * </p>
 * @author Mohammed Ammar
 * @since 14-11-2022
 * @version 1.0
 */
@Repository
public interface CartDetailsRepo extends JpaRepository<CartDetails, Integer> {

    /**
     * <p>
     *     This method is used to delete all cart details
     *     by user's id, of particular user when user needs to empty cart
     *     or if user placed order through cart
     * </p>
     * @param userId - user's id to delete cart
     */
    @Modifying
    @Transactional
    @Query(value = "update cart_details set is_active = 0 where cart_id = "
            + "(select id from cart where user_id = ?1 and is_active = 1 )",
            nativeQuery = true)
    void deleteCartDetailsByUserId(Integer userId);
}
