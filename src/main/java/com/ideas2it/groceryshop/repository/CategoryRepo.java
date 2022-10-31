package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
