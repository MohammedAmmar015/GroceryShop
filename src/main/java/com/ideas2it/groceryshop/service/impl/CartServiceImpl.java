package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CartDetailsRequestDto;
import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
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

import java.util.ArrayList;
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

    /**
     * <p>
     *     It is used to add Product to the Cart
     * </p>
     * @param cartRequest - product details to add into Cart
     * @param userId - user's id to add product to user's cart
     * @return - successDto with Message and status Code
     */
    @Override
    public SuccessDto addCart(CartRequestDto cartRequest, Integer userId) throws NotFound, Existed {
        Optional<User> mayBeUser = userHelper.findUserById(userId);
        if (mayBeUser.isEmpty()) {
            throw new NotFound("User Not Found");
        }
        User user = mayBeUser.get();
        if (!user.getRole().getName().equals("ROLE_CUSTOMER")) {
            throw new NotFound("Cart Not Found");
        }
        Optional<Cart> carts = cartRepo.findByUserIdAndIsActive(userId, true);
        Cart cart = addCartDetails(cartRequest, user, carts);
        cartRepo.save(cart);
        return new SuccessDto(200, "Product added to cart Successfully");
    }

    /**
     * <p>
     *     This method is used to add Product details to Cart,
     *     if cart not avilable, it will create new Cart
     *     and products to it
     * </p>
     * @param cartRequest cart details to add into Cart
     * @param user user details
     * @param carts carts container
     * @return Cart
     * @throws NotFound throws if product not found
     */
    private Cart addCartDetails(CartRequestDto cartRequest, User user, Optional<Cart> carts) throws NotFound, Existed {
        Cart cart;
        List<CartDetails> cartDetails;
        if (carts.isEmpty()) {
            cart = new Cart();
            cartDetails = new ArrayList<>();
            cart.setUser(user);
            cartDetails = addCartDetails(cartRequest.getCartDetails(),
                    cartDetails);
        } else {
            cart = carts.get();
            cartDetails = addCartDetails(cartRequest.getCartDetails(),
                    cart.getCartDetails());
        }
        cart.setCartDetails(cartDetails);
        cart.setTotalPrice(calculateTotalPrice(cartDetails));
        return cart;
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
    private List<CartDetails> addCartDetails(CartDetailsRequestDto cartDetailsRequest,
                                             List<CartDetails> cartDetails) throws NotFound, Existed {
        for (CartDetails cartDetail1 : cartDetails) {
            if (cartDetail1.getProduct().getId() == cartDetailsRequest.getProductId()) {
                throw new Existed("Already added to Cart");
            }
        }
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
    public CartResponseDto getCartByUserId(Integer userId) throws NotFound {
        Optional<Cart> cart = cartRepo.findByUserIdAndIsActive(userId, true);
        if (cart.isEmpty()) {
            throw new NotFound("Cart Not Found");
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
        if (user.isEmpty()) {
            throw new NotFound("User Not Found");
        }
        cartRepo.deleteCartDetailsByUserId(user.get().getId());
        cartRepo.deleteCartByUserId(user.get().getId());
        return new SuccessDto(200, "Cart Deleted Successfully");
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
        Optional<Cart> carts = cartRepo.findByUserIdAndIsActive(userId, true);
        if (carts.isEmpty()) {
            throw new NotFound("Cart Not Found");
        }
        Cart cart = carts.get();
        for (CartDetails cartDetails : cart.getCartDetails()) {
            if (cartDetails.getProduct().getId() == productId) {
                cartDetails.setIsActive(false);
                cart.setTotalPrice(cart.getTotalPrice() - cartDetails.getPrice());
            }
        }
        cartRepo.save(cart);
        return new SuccessDto(200, "Product from Cart Deleted Successfully");
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
    public SuccessDto updateCartByUser(CartRequestDto cartRequest, Integer userId) throws NotFound {
        Integer newQuantity = cartRequest.getCartDetails().getQuantity();
        Integer productId = cartRequest.getCartDetails().getProductId();
        Optional<Cart> cartContainer = cartRepo.findByUserIdAndIsActive(userId, true);
        if (cartContainer.isEmpty()) {
            throw new NotFound("Cart Not Found");
        }
        Cart cart = cartContainer.get();
        List<CartDetails> cartDetails = cart.getCartDetails();
        for (CartDetails cartDetail : cartDetails) {
            if (productId == cartDetail.getProduct().getId()) {
                cartDetail.setQuantity(newQuantity);
                cartDetail.setPrice(cartDetail.getProduct().getPrice() * newQuantity);
                cartDetails.remove(cartDetail);
                cartDetails.add(cartDetail);
                break;
            } else {
                throw new NotFound("Product Not Found");
            }
        }
        cart.setTotalPrice(calculateTotalPrice(cartDetails));
        cart.setCartDetails(cartDetails);
        cartRepo.save(cart);
        return new SuccessDto(200, "Quantity Updated successfully");
    }

    @Override
    public Cart getCartByCartId(Integer cartId, Boolean status) throws NotFound {
        Cart cart = cartRepo.findByIdAndIsActive(cartId, status);
        if (cart == null) {
            throw new NotFound("Cart Not Found");
        }
        return cart;
    }
}
