package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Integer> {
}
