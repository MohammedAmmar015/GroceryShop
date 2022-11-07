package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFoundException;

import java.util.List;

public interface ProductService {
    String addProduct(ProductRequestDto productRequestDto) throws Existed;

    List<ProductResponseDto> getProducts();

    ProductResponseDto getProductById(Integer id) throws NotFoundException;

    List<ProductResponseDto> getProductsByCategoryId(Integer categoryId) throws NotFoundException;

    List<ProductResponseDto> getProductsBySubCategoryId(Integer subCategoryId) throws NotFoundException;

    String deleteProductById(Integer id);

    String updateProductById(Integer id, ProductRequestDto productRequestDto);
}
