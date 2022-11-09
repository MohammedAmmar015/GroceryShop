package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author  RUBAN
 * @version  1.0 05/11/22
 */
public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product findByIdAndIsActive(Integer productId, Boolean status);

    @Query(value = "select * from product where is_active = ?1",
            nativeQuery = true)
    //@Query("Select p from Product p LEFT JOIN p.stocks s " +
            //" where p.isActive = ?1 and s.storeLocation.id = 1")
    /*@Query(value = "select *, s.available_stock as available_count from product p left join stock s on p.id = s.product_id where p.is_active = ?1 and s.location_id = 1",
          nativeQuery = true)
    */List<Product> findAllAndIsActive(Boolean status);

    List<Product> findByCategoryIdAndIsActive(Integer categoryId, Boolean status);

    @Query(value = "select * from product where sub_category_id = ?1", nativeQuery = true)
    List<Product> findBySubCategoryIDAndIsActive(Integer subCategoryId, Boolean status);

    List<Product> findAllProductByCategoryIdAndIsActive(Integer id, Boolean status);

    @Query(value  = "select * from product where sub_category_id = ?1 and is_active = ?2",
            nativeQuery = true)
    List<Product> findAllProductBySubCategoryIdAndIsActive(Integer subCategoryId, Boolean status);

    boolean existsByName(String name);
}


