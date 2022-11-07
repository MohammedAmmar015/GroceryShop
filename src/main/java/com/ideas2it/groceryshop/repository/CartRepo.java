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
    @Query(value = "update cart_details set is_active = 0 where cart_id = (select id from cart where user_id = ?1)", nativeQuery = true)
    void deleteCartDetailsByUserId(User user);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("Update Cart c set c.totalPrice = 0 where c.user = ?1")
    void deleteTotalPrice(User user);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update cart_details set quantity = ?1 where product_id = ?2 and cart_id = (select cart_id from cart where user_id = ?3)", nativeQuery = true)
    //@Query(value = "update Cart c set c.cartDetails.quantity = ?1 where c.cartDetails.product.id = ?2 AND user.id = ?3")
    void updateQuantityOfProductInCart(Integer quantity, Integer productId, Integer userId);
}
