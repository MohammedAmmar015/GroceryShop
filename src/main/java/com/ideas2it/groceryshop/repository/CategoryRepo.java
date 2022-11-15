package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  RUBAN
 * @version  1.0
 * @since 05/11/22
 */
@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {


    @Query(value = "select * from category  where parent_id is not null and is_active = ?1",
            nativeQuery = true)
     List<Category> findByParentIdSubCategoryAndIsActive(Boolean status);

    @Query(value = "select * from category where id = ?1 and is_active = ?2 and parent_id is null",
            nativeQuery = true)
    Category findByIdAndParentIdAndIsActive(Integer id, Boolean status);

    Category  findByIdAndIsActive(Integer id ,Boolean status);

    @Query(value ="select * from category where parent_id = ?1", nativeQuery = true)
    List<Category>  findSubCategoryByParentId(Integer id);

    @Query(value ="select * from category where parent_id is null and is_active = ?1",
            nativeQuery = true)
    List<Category> findByParentIdAndIsActive( Boolean status);

    @Query(value = "select * from category where parent_id = ?1 and id = ?2 and is_active = ?3",
            nativeQuery = true)
    Category findSubCategoryByParentIdAndCategoryIdAndIsActive(Integer parentId, Integer categoryId, Boolean status);

    @Query(value = "select * from category where id = ?1 and parent_id = ?2 and is_active = ?3",
            nativeQuery = true)
    Category findByCategoryIdAndParentIdAndIsActive(Integer categoryId, Integer parentId, Boolean status);

    Boolean existsByName(String name);
}



