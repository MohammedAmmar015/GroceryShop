package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.model.Product;

import java.util.List;

public interface ProductService {
    public String addProduct(ProductRequestDto productRequestDto);

    List<ProductResponseDto> getAll();

    ProductResponseDto getProductById(Integer id);
}
