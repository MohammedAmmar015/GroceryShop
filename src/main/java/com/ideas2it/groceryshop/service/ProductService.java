/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.Existed;
import com.ideas2it.groceryshop.exception.NotFound;

import java.util.List;

/**
 * @author  RUBAN 03/11/2022
 * @version   1.0
 */
public interface ProductService {

    SuccessResponseDto addProduct(ProductRequestDto productRequestDto) throws Existed, NotFound;

    List<ProductResponseDto> getProducts() throws NotFound;

    SuccessResponseDto deleteProductById(Integer id) throws NotFound;

    SuccessResponseDto updateProductById(Integer id, ProductRequestDto productRequestDto)
            throws NotFound, Existed;

    List<ProductResponseDto> getProductsByLocation(Integer locationId) throws NotFound;

    ProductResponseDto getProductById(Integer productId) throws NotFound;

    List<ProductResponseDto> getProductsByCategoryId(Integer categoryId) throws NotFound;

    List<ProductResponseDto> getProductsBySubCategoryId( Integer subCategoryId) throws NotFound;
}
