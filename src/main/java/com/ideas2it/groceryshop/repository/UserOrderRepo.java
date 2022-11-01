package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderRepo extends JpaRepository<UserOrder, Integer> {
}
