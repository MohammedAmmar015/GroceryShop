package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * <p>
 *     It implements method of CRUD operations for Product.
 * </p>
 * @author RUBAN  04/11/22
 * @version  1.0
 *
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public String insertProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.addProduct(productRequestDto);

    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponseDto> getProductsByCategoryId(@PathVariable("categoryId") Integer categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @GetMapping("/category/subCategory/{subCategoryId}")
    public List<ProductResponseDto> getProductsBySubCategoryId(@PathVariable("subCategoryId") Integer subCategoryId) {
        return productService.getProductsBySubCategoryId(subCategoryId);
    }

    @GetMapping("/")
    public List<ProductResponseDto> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") Integer id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable("id") Integer id) {
        return productService.deleteProductById(id);
    }
}
