package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Products, Integer> {
}
