package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductHelper {
    private ProductRepo productRepo;

    @Autowired
    public ProductHelper(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Product getProductById(Integer productId) {
        return productRepo.findByIdAndIsActive(productId, true);
    }
}
