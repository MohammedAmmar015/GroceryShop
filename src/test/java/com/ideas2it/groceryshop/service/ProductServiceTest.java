package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.mapper.ProductMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.StoreLocation;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ProductServiceTest.class})
public class ProductServiceTest {

    @Mock
    ProductRepo productRepo;

    @Mock
    StoreRepo storeRepo;

    @Mock
    StockRepo stockRepo;

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    @Test
    @Order(2)
    public void test_getProducts() throws NotFound {
        Category category1 = new Category(1,"fruits & vegetables", null, true);
        Category category = new Category(1,"vegetables", category1, true);
        Product product = new Product(1,"Tomato", 100, true, category, "1kg", "pic", 1, null);
        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productRepo.findAllAndIsActive(true)).thenReturn(products);
        assertEquals(1,productService.getProducts().size());
    }

    @Test
    @Order(3)
    public void test_getProductsByLocationIdAndCategoryId() throws NotFound {
        Category category1 = new Category(1,"bended", null, true);
        Category category = new Category(1,"bended", category1, true);
        List<StoreLocation> storeLocation = new ArrayList<>();
        StoreLocation storeLocation1 = new StoreLocation(1,60023, "guindy", true, null);

        storeLocation.add(storeLocation1);


        Product product = new Product(1,"medicine", 100, true, category, "1pcs", "pic", 1, storeLocation);

       // Product product1 = new Product(2,"Hamam", 100, true, category, "1pcs", "pic", 2, null);
        List<Product> products = new ArrayList<>();
        products.add(product);
       // products.add(product1);

        when(stockRepo.existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThanEqual(1,1,0)).thenReturn(true);
        when(storeRepo.existsByIdAndIsActive(1, true)).thenReturn(true);
        when(productRepo.findByCategoryIdAndIsActive( 1, true)).thenReturn(products);
        assertEquals(1,productService.getProductsByLocationIdAndCategoryId(1,1).size());
    }

    @Test
    @Order(1)
    public void test_addProduct() {
        ProductRequestDto productRequestDto = new ProductRequestDto(1, "Grapes", 100, "1kg",1,"pic");
        Product product = ProductMapper.toProduct(productRequestDto);

        when(productRepo.save(product)).thenReturn(product);
        assertEquals(100,productRepo.save(product).getPrice());
    }

    @Test
    @Order(4)
    public void test_updateProduct() {
        ProductRequestDto productRequestDto = new ProductRequestDto(1, "PineApple", 150, "1kg", 1,  "pic");
        Product product = new Product(1, "apple", 100, true, null, "1kg", "pic", 1, null);
        product.setName(productRequestDto.getName());

        when(productRepo.save(product)).thenReturn(product);
        assertEquals("PineApple", productRepo.save(product).getName());
    }

    @Test
    @Order(5)
    public void test_deleteProduct() {
        Product product = new Product(1, "apple", 100, true, null, "1kg", "pic", 1, null);
        product.setActive(false);

        when(productRepo.save(product)).thenReturn(product);
        assertEquals(false, productRepo.save(product).isActive());
    }
}

