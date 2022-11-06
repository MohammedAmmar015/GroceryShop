package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT * FROM category  WHERE parent_id is not null and is_active = ?1", nativeQuery = true)
     List<Category> findByParentIdSubCategoryAndIsActive(Boolean status);

    Category  findByIdAndIsActive(Integer id ,Boolean status);


    @Query(value ="select * from category where parent_id = ?1", nativeQuery = true)
    List<Category>  findSubCategoryByParentId(Integer id);

    @Query(value ="select * from category where parent_id is null and is_active = ?1", nativeQuery = true)
    List<Category> findByParentIdAndIsActive( Boolean status);


    @Query(value = "select * from category where id = ?1 AND parent_id = ?2", nativeQuery = true)
    Category findCategoryByParentIdAndId(Integer subCategoryId, Integer id);
}

