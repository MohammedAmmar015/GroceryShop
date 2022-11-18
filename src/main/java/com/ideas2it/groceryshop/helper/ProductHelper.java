/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author  RUBAN
 * @version  1.0
 * @since 05/11/22
 */
@Service
public class ProductHelper {
    private final ProductRepository productRepo;

    @Autowired
    public ProductHelper(ProductRepository productRepo) {
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

    /**
     * <p>
     *     This method will get product by category id and will delete, which will be useful
     *     for the delete category method in category service.
     * </p>
     * @param categoryId to get matched product object
     */
    public void getProductByCategoryIdAndSetFalse(Integer categoryId) {
        List<Product> products = productRepo.findProductsByCategoryIdAndIsActive(categoryId, true);
        for(Product product: products) {
            product.setActive(false);
            productRepo.save(product);
        }
    }

    /**
     * <p>
     *     This method will get product by sub category id and will delete, which will be useful
     *     for the delete category method in category service.It is intermediate method
     *     between category service to product repo
     * </p>
     * @param subCategoryId to get matched product object
     */
    public void getProductBySubCategoryIdAndSetFalse(Integer subCategoryId) {
        List<Product> products = productRepo.findBySubCategoryIdAndIsActive(subCategoryId, true);
        for(Product product: products) {
            product.setActive(false);
            productRepo.save(product);
        }
    }
}
