package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailsRepo extends JpaRepository<CartDetails, Integer> {
}
