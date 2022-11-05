package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT * FROM category  WHERE parent_id is not null ", nativeQuery = true)
     List<Category> findByParentId();

    @Query(value ="select * from category where parent_id is null", nativeQuery = true)
    List<Category> findByNotNull();
}
