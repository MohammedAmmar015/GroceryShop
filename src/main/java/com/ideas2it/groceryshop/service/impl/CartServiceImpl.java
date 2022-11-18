/*
 * <p>
 *      Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CartDetailsRequestDto;
import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.CartDetailsMapper;
import com.ideas2it.groceryshop.mapper.CartMapper;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.CartDetails;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.User;
import com.ideas2it.groceryshop.repository.CartRepo;
import com.ideas2it.groceryshop.service.CartDetailsService;
import com.ideas2it.groceryshop.service.CartService;
import com.ideas2it.groceryshop.service.ProductService;
import com.ideas2it.groceryshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * <p>
 *     This is Implementation class for Cart Service interface
 *     This class implements methods which is used to add products to
 *     user's cart, to delete user's cart, to delete product from user's cart
 *     and to update user's cart based currently logged-in user
 * </p>
 * @author Mohammed Ammar
 * @since 04-11-2022
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final Logger logger = LogManager.getLogger(CartServiceImpl.class);
    private final CartRepo cartRepo;
    private final UserService userService;
    private final ProductService productService;
    private final CartDetailsService cartDetailsService;

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto addOrModifyCart(CartRequestDto cartRequest)
                                      throws NotFoundException, ExistedException {
        logger.debug("Entered addOrModifyCart method in CartServiceImpl");
        Cart cart = getActiveCartOfCurrentUser();
        if (cart == null) {
            cart = CartMapper.toCart(cartRequest);
            cart.setUser(userService.getCurrentUser());
        }
        List<CartDetails> cartDetails
                = addProductToCartDetails(cartRequest.getCartDetails(),
                                          cart.getCartDetails());
        cart.setCartDetails(cartDetails);
        cart.setTotalPrice(calculateTotalPrice(cartDetails));
        cart.setTotalQuantity(calculateTotalQuantity(cartDetails));
        cartRepo.save(cart);
        logger.debug("product added to cart");
        return new SuccessResponseDto(201,
                                "Product added to cart successfully");
    }

    /**
     * <p>
     *     This method is used to find product and
     *     to verify if already exist in cart or not
     *     then add product details to Cart
     *     and also to calculate price based on quantity
     * </p>
     * @param cartDetailsRequest - cart details to add
     * @param cartDetails - List of cart details in user's cart
     * @return - List of cart details which added successfully
     * @throws NotFoundException if product not found
     * @throws ExistedException if product already exist in cart
     */
    private List<CartDetails> addProductToCartDetails
                              (CartDetailsRequestDto cartDetailsRequest,
                               List<CartDetails> cartDetails)
                               throws NotFoundException, ExistedException {
        logger.debug("Entered addProductToCartDetails private method in cartServiceImpl");
        for (CartDetails cartDetail : cartDetails) {
            if (cartDetail.getProduct().getId() == cartDetailsRequest.getProductId()) {
                logger.error("already added to cart");
                throw new ExistedException("Already added to cart");
            }
        }
        Product product = productService.getProductById(cartDetailsRequest.getProductId());
        if (product == null) {
            logger.error("product not found");
            throw new NotFoundException("Product not found");
        }
        CartDetails cartDetail = CartDetailsMapper.toCartDetails(cartDetailsRequest);
        cartDetail.setProduct(product);
        cartDetail.setPrice(product.getPrice() * cartDetail.getQuantity());
        cartDetails.add(cartDetail);
        return cartDetails;
    }

    /**
     * <p>
     *     This method is used to calculate total trice
     *     of all products available in Cart
     *     added by user
     * </p>
     * @param cartDetails cart details which has products added by user
     * @return totalPrice of all products added in cart
     */
    private Float calculateTotalPrice(List<CartDetails> cartDetails) {
        logger.debug("Entered calculateTotalPrice private method in cartServiceImpl");
        Float totalPrice = 0F;
        for (CartDetails cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice();
        }
        return totalPrice;
    }

    /**
     * <p>
     *     This method is used to calculate
     *     total quantity of products added into cart by user
     * </p>
     * @param cartDetails cart details with quantity
     * @return - totalQuantity
     */
    private Integer calculateTotalQuantity(List<CartDetails> cartDetails) {
        logger.debug("Entered calculateTotalQuantity private method in cartServiceImpl");
        Integer totalQuantity = 0;
        for (CartDetails cartDetail : cartDetails) {
            totalQuantity += cartDetail.getQuantity();
        }
        return totalQuantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CartResponseDto getCart() throws NotFoundException {
        logger.debug("Entered getCart method in cartServiceImpl");
        Cart cart = getActiveCartOfCurrentUser();
        if (cart == null) {
            throw new NotFoundException("Cart Not Found");
        }
        return CartMapper.toCartResponse(cart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto removeCart() {
        logger.debug("Entered removeCart method in cartServiceImpl");
        User user = userService.getCurrentUser();
        cartDetailsService.removeCartDetailsByUserId(user.getId());
        cartRepo.deleteCartByUserId(user.getId());
        logger.debug("cart deleted successfully");
        return new SuccessResponseDto(204,
                                "Cart deleted successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto removeProductFromCart(Integer productId)
                                                    throws NotFoundException {
        logger.debug("Entered removeProductFromCart method in cartServiceImpl");
        Cart cart = getActiveCartOfCurrentUser();
        if (cart == null) {
            logger.error("cart not found");
            throw new NotFoundException("Cart not found");
        }
        Boolean isDeleted = deleteProduct(productId, cart);
        if (!isDeleted) {
            logger.error("product not found");
            throw new NotFoundException("Product not found");
        }
        logger.debug("product from cart deleted successfully");
        return new SuccessResponseDto(200,
                                "Product from cart deleted successfully");
    }

    /**
     * <p>
     *     This method is used to delete product from Cart
     *     of particular user
     *     based on user id and product id
     *     if no product is found in cart, 
     *     then cart will also get deleted
     * </p>
     * @param productId - product id to delete from cart
     * @param cart - user's Cart
     * @return isDeleted or Not
     */
    private Boolean deleteProduct(Integer productId, Cart cart) {
        logger.debug("Entered deleteProduct private method in cartServiceImpl");
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
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto updateCartByUser(CartRequestDto cartRequest)
                                               throws NotFoundException {
        logger.debug("Entered updateCartByUser method in cartServiceImpl");
        Integer newQuantity = cartRequest.getCartDetails().getQuantity();
        Integer productId = cartRequest.getCartDetails().getProductId();
        Cart cart = getActiveCartOfCurrentUser();
        if (cart == null) {
            logger.error("cart not found");
            throw new NotFoundException("Cart not found");
        }
        List<CartDetails> cartDetails = cart.getCartDetails();
        cartDetails.stream()
                   .filter(cartDetail -> cartDetail.getProduct().getId() == productId)
                   .forEach(cartDetail ->
                            {
                                Float productPrice = cartDetail.getProduct().getPrice();
                                cartDetail.setQuantity(newQuantity);
                                cartDetail.setPrice(productPrice * newQuantity);
                            });
        cart.setTotalPrice(calculateTotalPrice(cartDetails));
        cart.setTotalQuantity(calculateTotalQuantity(cartDetails));
        cart.setCartDetails(cartDetails);
        cartRepo.save(cart);
        logger.debug("cart updated successfully");
        return new SuccessResponseDto(200,
                                "Quantity updated successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart getActiveCartOfCurrentUser() {
        logger.debug("Entered getCartByCartId method in cartServiceImpl");
        User user = userService.getCurrentUser();
        Optional<Cart> cart = cartRepo.findByUserIdAndIsActive(user.getId(),
                                                        true);
        if (cart.isEmpty()) {
            return null;
        }
        return cart.get();
    }
}
