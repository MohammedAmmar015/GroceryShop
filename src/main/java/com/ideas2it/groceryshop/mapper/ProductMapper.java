package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.model.Product;

public class ProductMapper {
    public static Product toProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        return product;

    }
}
