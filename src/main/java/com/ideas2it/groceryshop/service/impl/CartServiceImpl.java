package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CartDetailsRequestDto;
import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
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
import com.ideas2it.groceryshop.repository.CartDetailsRepo;
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
 *     This class is used to add, remove,
 *     update products from cart
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
    private CartDetailsRepo cartDetailsRepo;

    /**
     * <p>
     *     This method is used to add Product to the Cart
     *     of particular user based on user id
     *     and product id
     * </p>
     * @param cartRequest - product details to add into Cart
     * @param userId - user's id to add product to user's cart
     * @return - SuccessResponseDto with Message and status Code
     * @throws NotFound - if user or cart not found
     * @throws Existed if product already exist in cart
     */
    @Override
    public SuccessResponseDto addCart(CartRequestDto cartRequest, Integer userId) throws NotFound, Existed {
        Optional<User> mayBeUser = userHelper.findUserById(userId);
        if (mayBeUser.isEmpty()) {
            throw new NotFound("User Not Found");
        }
        User user = mayBeUser.get();
        if (!user.getRole().getName().equals("ROLE_CUSTOMER")) {
            throw new NotFound("Cart Not Found");
        }
        Optional<Cart> carts = cartRepo.findByUserIdAndIsActive(userId, true);
        Cart cart = createCart(cartRequest, user, carts);
        cartRepo.save(cart);
        return new SuccessResponseDto(200, "Product added to cart successfully");
    }

    /**
     * <p>
     *     This method is used to add Product details to Cart,
     *     if cart not available, it will create new Cart
     *     and add products to it
     * </p>
     * @param cartRequest cart details to add into Cart
     * @param user user details
     * @param carts carts container
     * @return Cart
     * @throws NotFound throws if product not found
     * @throws Existed if product already exist in Cart
     */
    private Cart createCart(CartRequestDto cartRequest, User user, Optional<Cart> carts) throws NotFound, Existed {
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
        cart.setTotalQuantity(calculateTotalQuantity(cartDetails));
        return cart;
    }

    /**
     * <p>
     *     It is used to find product and add product details to Cart details
     *     To calculate price based on quantity
     * </p>
     * @param cartDetailsRequest - cart details to add
     * @param cartDetails - List of cart details in user's cart
     * @return - List of cart details
     * @throws NotFound if product not found
     * @throws Existed if product already exist in cart
     */
    private List<CartDetails> addCartDetails(CartDetailsRequestDto cartDetailsRequest,
                                             List<CartDetails> cartDetails) throws NotFound, Existed {
        for (CartDetails cartDetail1 : cartDetails) {
            if (cartDetail1.getProduct().getId() == cartDetailsRequest.getProductId()) {
                throw new Existed("Already added to cart");
            }
        }
        CartDetails cartDetail = CartDetailsMapper.toCartDetails(cartDetailsRequest);
        Product product = productHelper.getProductById(cartDetailsRequest.getProductId());
        if (product == null) {
            throw new NotFound("Product not found");
        }
        cartDetail.setProduct(product);
        cartDetail.setPrice(product.getPrice() * cartDetail.getQuantity());
        cartDetails.add(cartDetail);
        return cartDetails;
    }

    /**
     * <p>
     *     This method is used to calculate total trice
     *     of all products in Cart
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
     *     This method is used to calculate
     *     total quantity of cart
     * </p>
     * @param cartDetails cart details with quantity
     * @return - totalQuantity
     */
    private Integer calculateTotalQuantity(List<CartDetails> cartDetails) {
        Integer totalQuantity = 0;
        for (CartDetails cartDetail : cartDetails) {
            totalQuantity += cartDetail.getQuantity();
        }
        return totalQuantity;
    }

    /**
     * <p>
     *     This method is used to get Cart of Particular user
     *     by user Id
     * </p>
     * @param userId - user's id to get Cart
     * @return - CartResponse with cart details
     * @throws NotFound if cart not found
     */
    @Override
    public CartResponseDto getCartByUserId(Integer userId) throws NotFound {
        Optional<Cart> cart = cartRepo.findByUserIdAndIsActive(userId, true);
        if (cart.isEmpty()) {
            throw new NotFound("Cart not found");
        }
        return CartMapper.toCartResponse(cart.get());
    }

    /**
     * <p>
     *     This method is used to remove all Cart details from Cart
     *     and to make total price to zero
     * </p>
     *
     * @param userId - user's id to remove products from cart
     * @return SuccessResponseDto if cart deleted successfully
     * @throws if cart not found
     */
    @Override
    public SuccessResponseDto removeCart(Integer userId) throws NotFound {
        Optional<User> user = userHelper.findUserById(userId);
        if (user.isEmpty()) {
            throw new NotFound("User not found");
        }
        cartDetailsRepo.deleteCartDetailsByUserId(user.get().getId());
        cartRepo.deleteCartByUserId(user.get().getId());
        return new SuccessResponseDto(200, "Cart deleted successfully");
    }

    /**
     * <p>
     *     This method is used to remove product from cart
     *     of particular user
     *     based on user id and product id
     * </p>
     *
     * @param userId    - user's id to remove product from cart
     * @param productId - product id to be removed
     * @return SuccessResponseDto if product deleted from cart
     * @throws NotFound if cart or product not found
     */
    @Override
    public SuccessResponseDto removeProductFromCart(Integer userId, Integer productId) throws NotFound {
        Optional<Cart> carts = cartRepo.findByUserIdAndIsActive(userId, true);
        if (carts.isEmpty()) {
            throw new NotFound("Cart not found");
        }
        Boolean isDeleted = deleteProduct(productId, carts);
        if (!isDeleted) {
            throw new NotFound("Product not found");
        }
        return new SuccessResponseDto(200, "Product from cart deleted successfully");
    }

    /**
     * <p>
     *     This method is used to delete product from Cart
     *     of particular user
     *     based on user id and product id
     * </p>
     * @param productId - product id to delete from cart
     * @param carts - user's Cart
     * @return isDeleted or Not
     */
    private Boolean deleteProduct(Integer productId, Optional<Cart> carts) {
        Cart cart = carts.get();
        Boolean isDeleted = false;
        for (CartDetails cartDetails : cart.getCartDetails()) {
            Integer itemsInCart = cart.getCartDetails().size();
            if (cartDetails.getProduct().getId() == productId) {
                if (itemsInCart == 1) {
                    cart.setIsActive(false);
                }
                cartDetails.setIsActive(false);
                isDeleted = true;
                cart.setTotalPrice(cart.getTotalPrice() - cartDetails.getPrice());
                cart.setTotalQuantity(cart.getTotalQuantity() - cartDetails.getQuantity());
            }
        }
        if (isDeleted) {
            cartRepo.save(cart);
        }
        return isDeleted;
    }

    /**
     * <p>
     *     This method is used to Update Cart Product Quantity of Particular user
     *     based on user id and cartRequest DTO
     * </p>
     *
     * @param cartRequest - cart details to be Updated
     * @param userId      - user's id to update cart product
     * @return SuccessResponseDto if cart updated successfully
     * @throws NotFound if cart or product not found
     */
    @Override
    public SuccessResponseDto updateCartByUser(CartRequestDto cartRequest, Integer userId) throws NotFound {
        Integer newQuantity = cartRequest.getCartDetails().getQuantity();
        Integer productId = cartRequest.getCartDetails().getProductId();
        Optional<Cart> cartContainer = cartRepo.findByUserIdAndIsActive(userId, true);
        if (cartContainer.isEmpty()) {
            throw new NotFound("Cart not found");
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
                throw new NotFound("Product not found");
            }
        }
        cart.setTotalPrice(calculateTotalPrice(cartDetails));
        cart.setCartDetails(cartDetails);
        cartRepo.save(cart);
        return new SuccessResponseDto(200, "Quantity updated successfully");
    }

    /**
     * <p>
     *     This method is used to get Cart by Cart Id
     * </p>
     * @param cartId - cart id to be retrieved
     * @param status - true or false
     * @return Cart
     * @throws NotFound - throws exception if cart not found
     */
    @Override
    public Cart getCartByCartId(Integer cartId, Boolean status) throws NotFound {
        Cart cart = cartRepo.findByIdAndIsActive(cartId, status);
        if (cart == null) {
            throw new NotFound("Cart not found");
        }
        return cart;
    }
}
