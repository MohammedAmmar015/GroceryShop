package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Integer> {
}
