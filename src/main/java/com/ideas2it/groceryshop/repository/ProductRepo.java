package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *     Thsi
 * </p>
 * @author  RUBAN
 * @version  1.0
 * @since 05/11/22
 */
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product findByIdAndIsActive(Integer productId, Boolean status);

    @Query(value = "select * from product where is_active = ?1",
            nativeQuery = true)
    List<Product> findAllAndIsActive(Boolean status);


    @Query(value ="select * from product p join category sc on sc.id = p.sub_category_id join category c on c.id = sc.parent_id where c.id = ?1 and p.is_active = true", nativeQuery = true)
    List<Product> findByCategoryIdAndIsActive(Integer categoryId, Boolean status);

    @Query(value ="select * from product p join category c on p.sub_category_id = c.id where c.id= ?1", nativeQuery = true)
    List<Product> findBySubCategoryIdAndIsActive( Integer subCategoryId, Boolean status);

    @Query(value = "select * from product where sub_category_id = ?1", nativeQuery = true)
    List<Product> findBySubCategoryIDAndIsActive(Integer subCategoryId, Boolean status);

    @Query(value ="select * from product p join category sc on sc.id = p.sub_category_id join category c on c.id = sc.parent_id where c.id = ?1 and p.is_active = true",
            nativeQuery = true)
    List<Product> findAllProductByCategory(Integer id);

    @Query(value  = "select * from product where sub_category_id = ?1",
            nativeQuery = true)
    List<Product> findAllProductBySubCategoryId(Integer categoryId);

    boolean existsByNameAndPriceAndUnit(String name, float price, String unit);

    boolean existsByName(String name);
}


