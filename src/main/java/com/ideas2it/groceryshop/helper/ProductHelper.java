/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author  RUBAN
 * @version  1.0
 * @since 05/11/22
 */
@Service
public class ProductHelper {
    private final ProductRepo productRepo;

    @Autowired
    public ProductHelper(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    /**
     * <p>
     *     This method used to get particular product by id.
     * </p>
     * @param productId to find object.
     * @return product.
     */
    public Product getProductById(Integer productId) {
        return productRepo.findByIdAndIsActive(productId, true);
    }
}
