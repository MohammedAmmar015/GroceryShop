package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.mapper.CategoryMapper;
import com.ideas2it.groceryshop.mapper.ProductMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.CategoryRepo;
import com.ideas2it.groceryshop.repository.ProductRepo;
import com.ideas2it.groceryshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductServiceImpl implements ProductService {

    private ProductRepo productRepo;

    private CategoryRepo categoryRepo;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }

    public String addProduct(ProductRequestDto productRequestDto) {
        Product product = ProductMapper.toProduct(productRequestDto);
        Optional<Category> category = categoryRepo.findById(productRequestDto.getCategoryId());
        product.setCategory(category.get());
        productRepo.save(product);
        return "saved";

    }



}
