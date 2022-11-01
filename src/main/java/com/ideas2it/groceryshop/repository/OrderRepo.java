package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {
}
