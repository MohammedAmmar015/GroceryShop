package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;

import java.util.List;

/**
 * @author  RUBAN 03/11/2022
 * @version   1.0
 */
public interface ProductService {
    SuccessDto addProduct(ProductRequestDto productRequestDto) throws Existed, NotFound;

    List<ProductResponseDto> getProducts() throws NotFound;

    List<ProductResponseDto> getProductsByCategoryId(Integer categoryId) throws NotFound;

    List<ProductResponseDto> getProductsBySubCategoryId(Integer subCategoryId) throws NotFound;

    SuccessDto deleteProductById(Integer id) throws NotFound;

    SuccessDto updateProductById(Integer id, ProductRequestDto productRequestDto) throws NotFound, Existed;

    List<ProductResponseDto> getProductsByLocation(Integer locationId) throws NotFound;
}
