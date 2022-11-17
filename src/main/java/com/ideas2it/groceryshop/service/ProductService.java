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
 * @author  RUBAN
 * @version   1.0
 * @since 03/11/22
 */
public interface ProductService {

    /**
     * <p>
     *     This method used to get products based on user search.
     * </p>
     * @param name to check with product name in database
     * @return if product name matching found will return it
     * @throws NotFound exception will be thrown if name doesn't match with any products
     */
    List<ProductResponseDto> getProductsBySearch(String name) throws NotFound;

    /**
     * <p>
     *     This method used to add product to the data base with validation.
     * </p>
     * @param productRequestDto contains dto type object then will convert into
     *                          model object using product mapper method and then save
     *                          in database
     * @return will return success message and status code
     * @throws Existed exception will be thrown if product already exist
     * @throws NotFound exception will b thrown if sub category id doesn't exist
     */
    SuccessResponseDto addProduct(ProductRequestDto productRequestDto) throws Existed, NotFound;

    /**
     * <p>
     *     This method used to get all products from the data base which are
     *     all active and return in dto type object.
     * </p>
     * @return List of products.
     * @throws NotFound exception will be thrown if the products doesn't exist.
     */
    List<ProductResponseDto> getProducts() throws NotFound;

    /**
     * <p>
     *     This method used to delete product using id
     *     and returns success response dto includes message and status code.
     * </p>
     * @param id to find particular object to get delete.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFound exception will be thrown if the id doesn't exist.
     */
    SuccessResponseDto deleteProductById(Integer id) throws NotFound;

    /**
     * <p>
     *     This method used to update product fields in data base before updating
     *     it will validate the product fields and then will update in data base.
     * </p>
     * @param id to find particular object.
     * @param productRequestDto dto type object contains values to get update.
     * @return SuccessDto otherwise exception will be thrown.
     * @throws NotFound exception will be thrown if the id doesn't exist.
     * @throws Existed exception will be thrown if the values to update is already exist.
     */
    SuccessResponseDto updateProductById(Integer id, ProductRequestDto productRequestDto)
            throws NotFound, Existed;

    /**
     * <p>
     *     This method used to get products in particular location
     *     using location id.
     * </p>
     * @param locationId to fetch particular product object
     * @return products
     * @throws NotFound exception will be thrown if products not exist
     */
    List<ProductResponseDto> getProductsByLocation(Integer locationId) throws NotFound;

    /**
     * <P>
     *     This method used to get particular product by id and convert it in to
     *     dto type object using product mapper and then returns it to product controller.
     * </P>
     * @param productId to fetch particular product object.
     * @return Dto type product
     * @throws NotFound exception will be thrown if id not exist.
     */
    ProductResponseDto getProductById(Integer productId) throws NotFound;

    /**
     * <p>
     *     This method used to get products of particular category using id and
     *     convert it to dto type object and it returns to product controller.
     * </p>
     * @param categoryId to fetch relevant object
     * @return list of products
     * @throws NotFound exception will be thrown if id not exist.
     */
    List<ProductResponseDto> getProductsByCategoryId(Integer categoryId) throws NotFound;

    /**
     * <p>
     *     This method used to get products of particular sub category using id and
     *     convert it to dto type object and it returns to product controller.
     * </p>
     * @param subCategoryId to fetch relevant object
     * @return list of products
     * @throws NotFound exception will be thrown if id not exist.
     */
    List<ProductResponseDto> getProductsBySubCategoryId( Integer subCategoryId) throws NotFound;
}
