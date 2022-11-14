package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CartDetailsRequestDto;
import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.helper.UserHelper;
import com.ideas2it.groceryshop.model.*;
import com.ideas2it.groceryshop.repository.CartRepo;
import com.ideas2it.groceryshop.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CartServiceTest {

    @InjectMocks
    private CartService cartService = new CartServiceImpl();

    @Mock
    private CartRepo cartRepo;

    @Mock
    private UserHelper userHelper;

    @Mock
    private ProductHelper productHelper;

    private Optional<User> user;

    private Optional<Cart> cart;
    private List<CartDetails> cartDetails = new ArrayList<>();
    private Product product;

    private CartRequestDto cartRequestDto;

    @BeforeEach
    void setUp() {
        CartDetailsRequestDto cartDetailsRequestDto = new CartDetailsRequestDto(1, 2);
        cartRequestDto = new CartRequestDto(cartDetailsRequestDto);
        user = Optional.of(new User(1, "test", "fname", "lname",
                        "pass", 9999999999L, "test@gmail.com",
                        true, new Role(1, "ROLE_CUSTOMER", true)));
        Category category = new Category();
        category.setId(1);
        category.setActive(true);
        category.setName("Fruits");
        Category subCategory = new Category();
        subCategory.setId(2);
        subCategory.setName("Fruits & Vegetables");
        subCategory.setCategory(category);
        product = new Product();
        product.setId(1);
        product.setPrice(50);
        product.setActive(true);
        product.setName("Apple");
        product.setUnit("1 KG");
        product.setCategory(subCategory);
        cartDetails.add(new CartDetails(1, 20, 100F, product, true));
        cart = Optional.of(new Cart(1, 200F, cartDetails, true, user.get()));
    }

    @Test
    public void testAddCart() throws Existed, NotFound {
        Integer userId = 1;
        Integer productId = 1;
        SuccessDto successDto = new SuccessDto(201, "Cart created successfully");
        when(userHelper.findUserById(userId)).thenReturn(user);
        when(cartRepo.findByUserIdAndIsActive(userId, true)).thenReturn(Optional.empty());
        when(productHelper.getProductById(productId)).thenReturn(product);
        when(cartService.addCart(cartRequestDto, userId)).thenReturn(successDto);
        SuccessDto result = cartService.addCart(cartRequestDto, userId);
        assertEquals(200, result.getStatusCode());
    }

    @Test
    public void testGetCartByUserId() throws NotFound {
        Integer userId = 1;
        when(cartRepo.findByUserIdAndIsActive(userId, true)).thenReturn(cart);
        assertEquals(cart.get().getId(), cartService.getCartByUserId(userId).getId());
    }
}
