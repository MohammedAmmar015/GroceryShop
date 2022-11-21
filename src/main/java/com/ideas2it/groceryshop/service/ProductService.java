/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.model.Product;

import java.util.List;

/**
 *  <p>
 *      provides create, view, update and delete operation service for
 *      the products.
 *  </p>
 *
 * @author  RUBAN
 * @version   1.0
 * @since 03/11/22
 */
public interface ProductService {

    /**
     * <p>
     *     To create product by using product request dto.
     * </p>
     *
     * @param productRequestDto - Contains name, price, subCategory id, unit, perHead.
     * @return - Success response dto with message and status code.
     * @throws ExistedException - If product already exist.
     * @throws NotFoundException - If sub category not found.
     */
    SuccessResponseDto addProduct(ProductRequestDto productRequestDto)
            throws ExistedException, NotFoundException;

    /**
     * <p>
     *     Get product by relevant to searching keywords.
     * </p>
     *
     * @param name - Contains keyword to search.
     * @return - Products if it matches with users given keywords.
     * @throws NotFoundException - If no products found for user search.
     */
    List<ProductResponseDto> getProductsBySearch(String name) throws NotFoundException;

    /**
     * <p>
     *     Get all the products available.
     * </p>
     *
     * @return - List of Product.
     * @throws NotFoundException - If no products found.
     */
    List<ProductResponseDto> getProducts() throws NotFoundException;

    /**
     * <p>
     *     Delete product by product id.
     * </p>
     *
     * @param id - To find correct product.
     * @return - Success response dto with message and status code.
     * @throws NotFoundException - If product id doesn't found.
     */
    SuccessResponseDto deleteProductById(Integer id) throws NotFoundException;

    /**
     * <p>
     *     Update product by using product id.
     * </p>
     *
     * @param id - To update relevant product.
     * @param productRequestDto - Contains name, price, unit, etc to be updated.
     * @return - Success response dto with message and status code.
     * @throws NotFoundException - If product not found.
     * @throws ExistedException - If field already exist.
     */
    SuccessResponseDto updateProductById(Integer id, ProductRequestDto productRequestDto)
            throws NotFoundException, ExistedException;

    /**
     * <p>
     *     Get products based on location id.
     * </p>
     *
     * @param locationId - To retrieve relevant product.
     * @return - List of available product.
     * @throws NotFoundException - If no product found.
     */
    List<ProductResponseDto> getProductsByLocation(Integer locationId) throws NotFoundException;

    /**
     * <p>
     *     Get product by id
     * </p>
     *
     * @param productId - To fetch particular product.
     * @return productResponseDto - Contains product details.
     * @throws NotFoundException - If product not found.
     */
    ProductResponseDto getProductById(Integer productId) throws NotFoundException;

    /**
     * <p>
     *     Get all products of particular category.
     * </p>
     *
     * @param categoryId - To find relevant products.
     * @return - list of product.
     * @throws NotFoundException - If product or category not found.
     */
    List<ProductResponseDto> getProductsByCategoryId(Integer categoryId) throws  NotFoundException;

    /**
     * <p>
     *     Get all product of particular sub category.
     * </p>
     *
     * @param subCategoryId - To find relevant products.
     * @return - list of product.
     * @throws NotFoundException - If subcategory or product not found.
     */
    List<ProductResponseDto> getProductsBySubCategoryId( Integer subCategoryId) throws NotFoundException;

    /**
     * <p>
     *     Get product by product id, used for cart service and order service.
     * </p>
     *
     * @param Id - To find particular product.
     * @return product - Contain product details.
     */
    Product getProductByProductId(Integer Id);
}
