/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.model.Product;

/**
 * <p>
 *     This class contains method to convert dto object to model object
 *     and model object to dto object.
 * </p>
 * @author RUBAN
 * @version 1.0
 * @since  03/11/22
 */
public class ProductMapper {

    /**
     * <p>
     *     This method will convert Dto object to model object.
     * </p>
     * @param productRequestDto Dto type object.
     * @return model object.
     */
    public static Product toProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setUnit(productRequestDto.getUnit());
        product.setPerHead(productRequestDto.getPerHead());
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        return product;
    }

    /**
     * <p>
     *     This method will convert Dto object to model object.
     * </p>
     * @param product model type object.
     * @return Dto object.
     */
    public static ProductResponseDto toProductDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setUnit(product.getUnit());
        productResponseDto.setPerHead(product.getPerHead());
        productResponseDto.setName(product.getName());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setCategoryName(product.getCategory().getCategory().getName());
        productResponseDto.setSubCategoryName(product.getCategory().getName());
        productResponseDto.setIsStockAvailable(true);
        return productResponseDto;
    }
}
