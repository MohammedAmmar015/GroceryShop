package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  RUBAN
 * @version  1.0 05/11/22
 */
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product findByIdAndIsActive(Integer productId, Boolean status);

    @Query(value = "select * from product where is_active = ?1",
            nativeQuery = true)
    List<Product> findAllAndIsActive(Boolean status);

    @Query("Select p from Product p LEFT JOIN p.storeLocations s " +
            " where p.isActive = ?1 and s.id = 1")
    List<Product> findByLocation( Boolean status);

    List<Product> findByCategoryIdAndIsActive(Integer categoryId, Boolean status);

    @Query(value = "select * from product where sub_category_id = ?1", nativeQuery = true)
    List<Product> findBySubCategoryIDAndIsActive(Integer subCategoryId, Boolean status);

    List<Product> findAllProductByCategoryIdAndIsActive(Integer id, Boolean status);

    @Query(value  = "select * from product where sub_category_id = ?1 and is_active = ?2",
            nativeQuery = true)
    List<Product> findAllProductBySubCategoryIdAndIsActive(Integer subCategoryId, Boolean status);

    boolean existsByName(String name);
}


