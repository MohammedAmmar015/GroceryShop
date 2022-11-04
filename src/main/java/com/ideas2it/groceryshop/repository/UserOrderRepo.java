package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrderRepo extends JpaRepository<UserOrder, Integer> {
    List<UserOrder> findByIsActive(Boolean status);

}
