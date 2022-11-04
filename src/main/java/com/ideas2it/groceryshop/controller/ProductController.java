package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public String insertProduct (@RequestBody ProductRequestDto productRequestDto) {
        return productService.addProduct(productRequestDto);

    }

}
