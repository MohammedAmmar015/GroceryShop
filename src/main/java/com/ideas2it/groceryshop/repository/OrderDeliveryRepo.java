package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.OrderDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDeliveryRepo extends JpaRepository<OrderDelivery, Integer> {
}
