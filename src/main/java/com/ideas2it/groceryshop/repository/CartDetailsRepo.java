package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * <p>
 *     This is CartDetails Repository, to do CRUD Operation on
 *     CartDetails Entity
 * </p>
 * @author Mohammed Ammar
 * @since 14-11-2022
 * @version 1.0
 */
@Repository
public interface CartDetailsRepo extends JpaRepository<CartDetails, Integer> {

    /**
     * <p>
     *     To Delete Cart details of particular user by user Id
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
