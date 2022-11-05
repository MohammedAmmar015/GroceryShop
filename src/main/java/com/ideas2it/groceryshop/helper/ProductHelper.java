package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  RUBAN
 * @version  1.0 05/11/22
 *
 */
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
