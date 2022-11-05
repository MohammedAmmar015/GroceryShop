package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.OrderDetails;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Integer> {

    List<OrderDetails> findByProduct(Product product);
}
