/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

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

    /**
     * <p>
     *     It is used to retrieve active Cart by user id
     * </p>
     * @param userId - user's id to retrieve Cart
     * @param isActive - true or false
     * @return - Cart object
     */
    Optional<Cart> findByUserIdAndIsActive(Integer userId, Boolean isActive);


    /**
     * <p>
     *     This method is used to delete cart by user id 
     * </p>
     * @param userId user's id to delete cart
     */
    @Modifying
    @Transactional
    @Query("Update Cart c set c.isActive = false where c.user.id = ?1")
    void deleteCartByUserId(Integer userId);
}
