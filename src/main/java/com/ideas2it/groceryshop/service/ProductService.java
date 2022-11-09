package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;

import java.util.List;

/**
 * @author  RUBAN 03/11/2022
 * @version   1.0
 */
public interface ProductService {
    SuccessDto addProduct(ProductRequestDto productRequestDto) throws ExistedException;

    List<ProductResponseDto> getProducts() throws NotFoundException;

    ProductResponseDto getProductById(Integer id) throws NotFoundException;

    List<ProductResponseDto> getProductsByCategoryId(Integer categoryId) throws NotFoundException;

    List<ProductResponseDto> getProductsBySubCategoryId(Integer subCategoryId) throws NotFoundException;

    SuccessDto deleteProductById(Integer id) throws NotFoundException;

    SuccessDto updateProductById(Integer id, ProductRequestDto productRequestDto) throws NotFoundException, ExistedException;
}
