package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.mapper.ProductMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.repository.CategoryRepo;
import com.ideas2it.groceryshop.repository.ProductRepo;
import com.ideas2it.groceryshop.repository.StockRepo;
import com.ideas2it.groceryshop.repository.StoreRepo;
import com.ideas2it.groceryshop.service.impl.ProductServiceImpl;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * <p>
 *     This class will test all method of service layer whether the desired
 *     output getting or not.
 * </p>
 *
 * @author  RUBAN J
 * @version 1.0
 * @since  03/11/22
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ProductServiceTest.class})
public class ProductServiceTest {
    @Mock
    ProductRepo productRepo;

    @Mock
    StoreRepo storeRepo;

    @Mock
    StockRepo stockRepo;

    @Mock
    CategoryRepo categoryRepo;

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    /**
     * <p>
     *     This Method is to test whether products retrieve method runs
     *     properly or not.
     * </p>
     * @throws NotFound exception will be thrown if products not found.
     */
    @Test
    @Order(2)
    public void test_getProducts() throws NotFound {
        Category category1 = new Category(1,"fruits & vegetables", null, true);
        Category category = new Category(1,"vegetables", category1, true);
        Product product = new Product(1,"Tomato", 100, true, category, "kg", 1);
        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productRepo.findAllAndIsActive(true)).thenReturn(products);
        assertEquals(1,productService.getProducts());
    }

    /**
     * <p>
     *     This testing Method will ensure getting products by location and
     *     category works or not.
     * </p>
     * @throws NotFound exception will be thrown if location or category id
     *         not found.
     */
    @Test
    @Order(3)
    public void test_getProductsByLocationIdAndCategoryId() throws NotFound {
        Category category1 = new Category(1,"bended", null, true);
        Category category = new Category(1,"bended", category1, true);
        List<StoreLocation> storeLocation = new ArrayList<>();
        Product product = new Product(1,"medicine", 100, true, category, "pcs", 1);
        List<Product> products = new ArrayList<>();
        products.add(product);

        when(stockRepo.existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThan(1,1,0)).thenReturn(true);
        when(storeRepo.existsByIdAndIsActive(1, true)).thenReturn(true);
        when(productRepo.findByCategoryIdAndIsActive( 1, true)).thenReturn(products);
        assertEquals(1,productService.getProductsByLocationIdAndCategoryId(1,1).size());
    }

    /**
     * <p>
     *     This Method is for testing add product method whether products
     *     are inserting properly or not.
     * </p>
     * @throws NotFound exception will be thrown if the category id not found.
     * @throws Existed exception will be thrown if products already exist.
     */
    @Test
    @Order(1)
    public void test_addProduct() throws NotFound, Existed {
        ProductRequestDto productRequestDto = new ProductRequestDto(1, "Grapes", 100, "kg",1,1);
        Product product = ProductMapper.toProduct(productRequestDto);
        Category category = new Category();

        when(categoryRepo.findById(productRequestDto.getSubCategoryId())).thenReturn(Optional.of(category));
        when(categoryRepo.existsById(productRequestDto.getSubCategoryId())).thenReturn(true);
        when(productRepo.save(product)).thenReturn(product);
        assertEquals(201,productService.addProduct(productRequestDto).getStatusCode());
    }

    /**
     * <p>
     *     This method will test whether updating products method works
     *     properly or not.
     * </p>
     * @throws Existed exception will be thrown if the update filed already exist.
     * @throws NotFound exception will be thrown if the id is not found.
     */
    @Test
    @Order(4)
    public void test_updateProduct() throws Existed, NotFound {
        ProductRequestDto productRequestDto = new ProductRequestDto(1, "PineApple", 150, "kg", 1, 1);
        Product product = new Product(1, "apple", 100, true, null, "kg", 1);
        product.setName(productRequestDto.getName());

        when(productRepo.findByIdAndIsActive(1, true)).thenReturn(product);
        assertEquals("Product details updated successfully", productService.updateProductById(1, productRequestDto).getMessage());
    }

    /**
     * <p>
     *     This method is for testing delete method works properly
     *     or not.
     * </p>
     * @throws NotFound exception will be thrown if the id not found.
     */
    @Test
    @Order(5)
    public void test_deleteProduct() throws NotFound {
        Product product = new Product(1, "apple", 100, true, null, "kg", 1);
        product.setActive(false);

        when(productRepo.findByIdAndIsActive(2, true)).thenReturn(product);
        assertEquals(200, productService.deleteProductById(2).getStatusCode());
    }
}

