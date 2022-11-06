package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/")
    public List<ProductResponseDto> getAll() {
        return productService.getAll();
    }

    @GetMapping("/category/subCategory/{subCategoryId}")
    public List<ProductResponseDto> getProductsBySubCategoryId(@PathVariable("subCategoryId") Integer subCategoryId) {
        return productService.getProductsBySubCategoryId(subCategoryId);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") Integer id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        return productService.deleteProduct(id);
    }
}
