package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.model.Product;

import java.util.List;

public interface ProductService {
    String addProduct(ProductRequestDto productRequestDto);

    List<ProductResponseDto> getProducts();

    ProductResponseDto getProductById(Integer id);

    List<ProductResponseDto> getProductsByCategoryId(Integer categoryId);

    List<ProductResponseDto> getProductsBySubCategoryId(Integer subCategoryId);

    String deleteProductById(Integer id);
}
