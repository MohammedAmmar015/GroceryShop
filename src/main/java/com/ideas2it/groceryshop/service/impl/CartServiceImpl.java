package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CartDetailsRequest;
import com.ideas2it.groceryshop.dto.CartRequest;
import com.ideas2it.groceryshop.dto.CartResponse;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.helper.CartHelper;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.helper.UserHelper;
import com.ideas2it.groceryshop.mapper.CartDetailsMapper;
import com.ideas2it.groceryshop.mapper.CartMapper;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.CartDetails;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.User;
import com.ideas2it.groceryshop.repository.CartRepo;
import com.ideas2it.groceryshop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *     Implementation class for Cart Service
 * </p>
 * @author Mohammed Ammar
 * @since 04-11-2022
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private CartRepo cartRepo;
    private UserHelper userHelper;
    private ProductHelper productHelper;
    private CartHelper cartHelper;

    /**
     * <p>
     *     It is used to add Product to the Cart
     * </p>
     * @param cartRequest - product details to add into Cart
     * @param userId - user's id to add product to user's cart
     * @return - successDto with Message and status Code
     */
    @Override
    public SuccessDto addCart(CartRequest cartRequest, Integer userId) {
        User user = userHelper.findUserById(userId);
        Cart cart = cartRepo.findByUserIdAndIsActive(user.getId(), true);
        List<CartDetails> cartDetails = addCartDetails(cartRequest.getCartDetails(),
                                                        cart.getCartDetails());
        cart.setCartDetails(cartDetails);
        cart.setTotalPrice(calculateTotalPrice(cartDetails));
        cartRepo.save(cart);
        return new SuccessDto(200, "Product added to cart Successfully");
    }

    private List<CartDetails> addCartDetails(CartDetailsRequest cartDetailsRequest,
                                            List<CartDetails> cartDetails) {
        CartDetails cartDetail = CartDetailsMapper.toCartDetails(cartDetailsRequest);
        Product product = productHelper.getProductById(cartDetailsRequest.getProductId());
        cartDetail.setProduct(product);
        cartDetail.setPrice(product.getPrice() * cartDetail.getQuantity());
        cartDetails.add(cartDetail);
        return cartDetails;
    }

    private Float calculateTotalPrice(List<CartDetails> cartDetails) {
        Float totalPrice = 0F;
        for (CartDetails cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice();
        }
        return totalPrice;
    }

    /**
     * <p>
     *     It is used to get Cart of Particular user by Id
     * </p>
     * @param userId - user's id to get Cart
     * @return - CartResponse with cart details
     */
    @Override
    public CartResponse getCartByUserId(Integer userId) {
        Cart cart = cartRepo.findByUserIdAndIsActive(userId, true);
        return CartMapper.convertCartToCartResponse(cart);
    }

    /**
     * <p>
     *     It is used To remove Cart details from Cart
     * </p>
     * @param userId - user's id to remove products from cart
     */
    @Override
    public void removeCart(Integer userId) {
        User user = userHelper.findUserById(userId);
        cartHelper.deleteAllProductsFromCart(user);
    }

    /**
     * <p>
     *     It is used to remove particular product from cart
     * </p>
     * @param userId - user's id to remove product from cart
     * @param productId - product id to be removed
     */
    @Override
    public void removeProductFromCart(Integer userId, Integer productId) {
        Cart cart = cartRepo.findByUserIdAndIsActive(userId, true);
        for (CartDetails cartDetails : cart.getCartDetails()) {
            if (cartDetails.getProduct().getId() == productId) {
                cartDetails.setIsActive(false);
                cart.setTotalPrice(cart.getTotalPrice() - cartDetails.getPrice());
            }
        }
        cartRepo.save(cart);
    }

    /**
     * <p>
     *     It is used to Update Cart Product Quantity of Particular user
     * </p>
     * @param cartRequest - cart details to be Updated
     * @param userId - user's id to update cart product
     */
    @Override
    public void updateCartByUser(CartRequest cartRequest, Integer userId) {
        Integer newQuantity = cartRequest.getCartDetails().getQuantity();
        Integer productId = cartRequest.getCartDetails().getProductId();
        Cart cart = cartRepo.findByUserIdAndIsActive(userId, true);
        List<CartDetails> cartDetails = cart.getCartDetails();
        for (CartDetails cartDetail : cartDetails) {
            if (productId == cartDetail.getProduct().getId()) {
                cartDetail.setQuantity(newQuantity);
                cartDetail.setPrice(cartDetail.getProduct().getPrice() * newQuantity);
                cartDetails.remove(cartDetail);
                cartDetails.add(cartDetail);
            }
        }
        cart.setTotalPrice(calculateTotalPrice(cartDetails));
        cart.setCartDetails(cartDetails);
        cartRepo.save(cart);
    }
}
