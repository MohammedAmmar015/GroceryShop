/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author  RUBAN
 * @version  1.0
 * @since 05/11/22
 */
@Component
public class ProductHelper {

    private final ProductService productService;

    @Autowired
    public ProductHelper(ProductService productService) {
        this.productService = productService;
    }

    /**
     * <p>
     *     This method used to get particular product by id.
     * </p>
     * @param productId to find object.
     * @return product.
     */
    public Product getProductById(Integer productId) {
        //return productRepo.findByIdAndIsActive(productId, true);
        return productService.getProductById(productId);
    }

    /**
     * <p>
     *     This method will get product by category id and will delete(soft delete) it, which will be
     *     useful for the delete category method in category service.
     * </p>
     * @param categoryId to get matched product object
     */
    public void getProductByCategoryIdAndSetFalse(Integer categoryId) {
        productService.getProductByCategoryId(categoryId);
    }

    /**
     * <p>
     *     This method will get product by sub category id and will delete it, which will be useful
     *     for the delete category method in category service.It is intermediate method
     *     between category service to product repo
     * </p>
     * @param subCategoryId to get matched product object
     */
    public void getProductBySubCategoryIdAndSetFalse(Integer subCategoryId) {
        productService.getProductBySubCategoryId(subCategoryId);
    }
}
