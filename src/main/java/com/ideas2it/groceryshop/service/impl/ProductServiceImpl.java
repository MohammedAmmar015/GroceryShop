package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.helper.ProductHelper;
import com.ideas2it.groceryshop.mapper.ProductMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.CategoryRepo;
import com.ideas2it.groceryshop.repository.ProductRepo;
import com.ideas2it.groceryshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductServiceImpl implements ProductService {

    private ProductRepo productRepo;

    private CategoryRepo categoryRepo;

    private ProductHelper productHelper;



    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo, ProductHelper productHelper) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.productHelper = productHelper;

    }

    public String addProduct(ProductRequestDto productRequestDto) {
        Product product = ProductMapper.toProduct(productRequestDto);
        Optional<Category> category = categoryRepo.findById(productRequestDto.getCategoryId());
        product.setCategory(category.get());
        productRepo.save(product);
        return "saved";

    }

    public List<ProductResponseDto> getAll() {

        List<Product> product = productRepo.findAll();
        List<ProductResponseDto> productResponseDto = new ArrayList<>();
        for (Product products :product) {
            productResponseDto.add(ProductMapper.toProductDto(products));
        }
         return productResponseDto;
    }

    public ProductResponseDto getProductById(Integer id) {
       Product product = productHelper.getProductById(id);
        return ProductMapper.toProductDto(product);

    }



}
