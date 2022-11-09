package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CartDetailsRequest;
import com.ideas2it.groceryshop.dto.CartRequest;
import com.ideas2it.groceryshop.dto.CartResponse;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.NotFound;
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
import java.util.Optional;


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
    public SuccessDto addCart(CartRequest cartRequest, Integer userId) throws NotFound {
        Optional<User> user = userHelper.findUserById(userId);
        if (user.isEmpty()) {
            throw new NotFound("User Not Found");
        }
        Optional<Cart> carts = cartRepo.findByUserIdAndIsActive(userId, true);
        if (carts.isEmpty()) {
            throw new NotFound("Cart Not Found, Cart is Only for Customer");
        } else {
            Cart cart = carts.get();
            List<CartDetails> cartDetails = addCartDetails(cartRequest.getCartDetails(),
                    cart.getCartDetails());
            cart.setCartDetails(cartDetails);
            cart.setTotalPrice(calculateTotalPrice(cartDetails));
            cartRepo.save(cart);
        }
        return new SuccessDto(200, "Product added to cart Successfully");
    }

    /**
     * <p>
     *     It is used to add product details to Cart details
     *     To calculate price based on quantity
     * </p>
     * @param cartDetailsRequest - cart details to add
     * @param cartDetails - List of cart details in user's cart
     * @return - List<CartDetails>
     */
    private List<CartDetails> addCartDetails(CartDetailsRequest cartDetailsRequest,
                                            List<CartDetails> cartDetails) throws NotFound {
        CartDetails cartDetail = CartDetailsMapper.toCartDetails(cartDetailsRequest);
        Product product = productHelper.getProductById(cartDetailsRequest.getProductId());
        if (product == null) {
            throw new NotFound("Product Not Found");
        }
        cartDetail.setProduct(product);
        cartDetail.setPrice(product.getPrice() * cartDetail.getQuantity());
        cartDetails.add(cartDetail);
        return cartDetails;
    }

    /**
     * <p>
     *     To Calculate Total Price of products in Cart
     * </p>
     * @param cartDetails cart details which has products
     * @return Float - totalPrice
     */
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
    public CartResponse getCartByUserId(Integer userId) throws NotFound {
        Optional<Cart> cart = cartRepo.findByUserIdAndIsActive(userId, true);
        if (cart.isEmpty()) {
            throw new NotFound("Cart Not Found, Cart is Only for Customer");
        }
        return CartMapper.convertCartToCartResponse(cart.get());
    }

    /**
     * <p>
     * It is used To remove Cart details from Cart
     * </p>
     *
     * @param userId - user's id to remove products from cart
     * @return
     */
    @Override
    public SuccessDto removeCart(Integer userId) throws NotFound {
        Optional<User> user = userHelper.findUserById(userId);
        if (user == null) {
            throw new NotFound("User Not Found");
        }
        cartRepo.deleteCartDetailsByUserId(user.get().getId());
        cartRepo.deleteTotalPrice(user.get());
        return new SuccessDto(200, "All products from Cart, gets Deleted");
    }

    /**
     * <p>
     * It is used to remove particular product from cart
     * </p>
     *
     * @param userId    - user's id to remove product from cart
     * @param productId - product id to be removed
     * @return
     */
    @Override
    public SuccessDto removeProductFromCart(Integer userId, Integer productId) throws NotFound {
        Cart cart = cartRepo.findByUserIdAndIsActive(userId, true).get();
        if (cart == null) {
            throw new NotFound("Cart Not Found, Cart is Only for Customer");
        }
        for (CartDetails cartDetails : cart.getCartDetails()) {
            if (cartDetails.getProduct().getId() == productId) {
                cartDetails.setIsActive(false);
                cart.setTotalPrice(cart.getTotalPrice() - cartDetails.getPrice());
            }
        }
        cartRepo.save(cart);
        return new SuccessDto(200, "Product in Cart gets Deleted Successfully");
    }

    /**
     * <p>
     * It is used to Update Cart Product Quantity of Particular user
     * </p>
     *
     * @param cartRequest - cart details to be Updated
     * @param userId      - user's id to update cart product
     * @return
     */
    @Override
    public SuccessDto updateCartByUser(CartRequest cartRequest, Integer userId) {
        Integer newQuantity = cartRequest.getCartDetails().getQuantity();
        Integer productId = cartRequest.getCartDetails().getProductId();
        Cart cart = cartRepo.findByUserIdAndIsActive(userId, true).get();
        List<CartDetails> cartDetails = cart.getCartDetails();
        for (CartDetails cartDetail : cartDetails) {
            if (productId == cartDetail.getProduct().getId()) {
                cartDetail.setQuantity(newQuantity);
                cartDetail.setPrice(cartDetail.getProduct().getPrice() * newQuantity);
                cartDetails.remove(cartDetail);
                cartDetails.add(cartDetail);
                break;
            }
        }
        cart.setTotalPrice(calculateTotalPrice(cartDetails));
        cart.setCartDetails(cartDetails);
        cartRepo.save(cart);
        return new SuccessDto(200, "Product Quantity updated in Cart successfully");
    }

    @Override
    public Cart getCartByCartId(Integer cartId, Boolean status) throws NotFound {
        Cart cart = cartRepo.findByIdAndIsActive(cartId, status);
        if (cart == null) {
            throw new NotFound("Cart not found for given Cart id");
        }
        return cart;
    }
}
