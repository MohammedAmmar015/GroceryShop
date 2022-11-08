package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.model.Product;

import java.util.List;

public class ProductMapper {
    public static Product toProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setUnit(productRequestDto.getUnit());
        product.setName(productRequestDto.getName());
        product.setCreatedBy(productRequestDto.getCreatedBy());
        product.setModifiedBy(productRequestDto.getCreatedBy());
        product.setPrice(productRequestDto.getPrice());
        return product;

    }

    public static ProductResponseDto toProductDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setUnit(product.getUnit());
        productResponseDto.setName(product.getName());
        productResponseDto.setPrice(product.getPrice());
        return productResponseDto;

    }

   /* public static ProductResponseDto toProductDto(List<Product> product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        for (Product products : product) {
            productResponseDto.setId(products.getId());
            productResponseDto.setName(products.getName());
            productResponseDto.setPrice(products.getPrice());
        }
        return productResponseDto;
    }*/
}
