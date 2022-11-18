package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.repository.CartDetailsRepo;
import com.ideas2it.groceryshop.service.CartDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 *     This is Implementation class for CartDetailsService interface
 *     This class implements methods which is used to add products to
 *     user's cart, to delete cart, to delete product from cart
 *     and to update cart based currently logged-in user
 * </p>
 * @author Mohammed Ammar
 * @since 04-11-2022
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CartDetailsServiceImpl implements CartDetailsService {

    private final CartDetailsRepo cartDetailsRepo;

    /**
     * <p>
     *     This method is used to remove all products from user's
     *     cart based on user's id
     * </p>
     * @param userId user's id to delete products from cart
     */
    @Override
    public void removeCartDetailsByUserId(Integer userId) {
        cartDetailsRepo.deleteCartDetailsByUserId(userId);
    }
}
