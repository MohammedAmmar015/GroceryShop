package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product findByIdAndIsActive(Integer productId, Boolean status);

    @Query(value = "select * from product where is_active = ?1", nativeQuery = true)
    List<Product> findAllAndIsActive(Boolean status);

    List<Product> findByCategoryIdAndIsActive(Integer categoryId, Boolean status);

    @Query(value = "select * from product where sub_category_id = ?1", nativeQuery = true)
    List<Product> findBySubCategoryIDAndIsActive(Integer subCategoryId, Boolean status);

    List<Product> findAllProductByCategoryIdAndIsActive(Integer id, Boolean status);

    @Query(value  = "select * from product where sub_category_id = ?1 AND is_active = ?2",
            nativeQuery = true)
    List<Product> findAllProductBySubCategoryIdAndIsActive(Integer subCategoryId, Boolean status);

    boolean existsByName(String name);
}


