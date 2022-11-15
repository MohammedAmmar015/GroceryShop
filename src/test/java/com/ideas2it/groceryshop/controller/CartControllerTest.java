package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.*;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * <p>
 *     This is Test Class for Cart Controller
 *     used to test methods in controller
 * </p>
 * @author Mohammed Ammar
 * @since 15-11-2022
 * @version 1.0
 */
@SpringBootTest
public class CartControllerTest {

    @InjectMocks
    CartController cartController;

    @Mock
    CartService cartService;

    /**
     * <p>
     *     This method is used to test createCart() method
     *     in cart controller
     * </p>
     * @throws Existed - exception will be thrown if product already exists in Cart
     * @throws NotFound - exception will be thrown if product not found
     */
    @Test
    public void testCreateCart() throws Existed, NotFound {
        Integer userId = 1;
        CartDetailsRequestDto cartDetail = new CartDetailsRequestDto(1, 10);
        CartRequestDto cartRequestDto = new CartRequestDto(cartDetail);
        SuccessResponseDto successDto = new SuccessResponseDto(201,"Cart created successfully");
        when(cartService.addCart(cartRequestDto, userId)).thenReturn(successDto);
        SuccessResponseDto result = cartController.createCart(cartRequestDto, userId);
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }

    /**
     * <p>
     *     This method is used to test viewCart method
     *     in Cart Controller
     * </p>
     * @throws NotFound - exception will be thrown if Cart not found (if it's admin userId)
     */
    @Test
    public void testViewCart() throws NotFound {
        Integer userId = 1;
        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setId(1);
        cartResponseDto.setCreatedAt(new Date(2022-12-02));
        cartResponseDto.setTotalPrice(200F);
        CartDetailsResponseDto cartDetail = new CartDetailsResponseDto();
        cartDetail.setProductName("GoodDay");
        cartDetail.setSubCategory("Cookies");
        cartDetail.setCategory("Biscuit");
        cartDetail.setPrice(100F);
        cartDetail.setQuantity(2);
        cartResponseDto.setCartDetails(List.of(cartDetail));
        when(cartService.getCartByUserId(userId)).thenReturn(cartResponseDto);
        CartResponseDto result = cartController.viewCarts(userId);
        assertEquals(cartResponseDto.getId(), result.getId());
    }

    /**
     * <p>
     *     This method is used to test updateCart() method,
     *     in Cart Controller
     * </p>
     * @throws NotFound - exception will be thrown if Cart not found
     */
    @Test
    public void testUpdateCart() throws NotFound {
        Integer userId = 1;
        CartDetailsRequestDto cartDetail = new CartDetailsRequestDto(1, 10);
        CartRequestDto cartRequestDto = new CartRequestDto(cartDetail);
        SuccessResponseDto successDto = new SuccessResponseDto(200,"Cart Updated successfully");
        when(cartService.updateCartByUser(cartRequestDto, userId)).thenReturn(successDto);
        SuccessResponseDto result = cartController.updateCart(cartRequestDto, userId);
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }

    /**
     * <p>
     *     This method is used to test deleteCart()
     *     method in Cart Controller
     * </p>
     * @throws NotFound - exception will be thrown if Cart not found
     */
    @Test
    public void testDeleteCart() throws NotFound {
        Integer userId = 1;
        SuccessResponseDto successDto = new SuccessResponseDto(200,"Cart deleted successfully");
        when(cartService.removeCart(userId)).thenReturn(successDto);
        SuccessResponseDto result = cartController.deleteCart(userId);
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }

    /**
     * <p>
     *     This method is used to test deleteProductFromCart()
     *     method in Cart Controller
     * </p>
     * @throws NotFound - exception will be thrown if Cart not found
     */
    @Test
    public void testDeleteProductFromCart() throws NotFound {
        Integer userId = 1;
        Integer productId = 1;
        SuccessResponseDto successDto = new SuccessResponseDto(200,"Product from Cart deleted successfully");
        when(cartService.removeProductFromCart(userId, productId)).thenReturn(successDto);
        SuccessResponseDto result = cartController.deleteProductFromCart(userId, productId);
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }
}
