package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface StockRepo extends JpaRepository<Stock, Integer> {
    List<Stock> findByProductId(Integer productId);
    Stock findByProductIdAndStoreLocationId(Integer productId, Integer locationId);

    @Query("UPDATE Stock s SET s.availableStock = s.availableStock + ?1 where s.product.id = ?2")
    @Modifying(clearAutomatically = true)
    @Transactional
    Integer updateStockByProduct(Integer availableStock, Integer productId);

    @Query("UPDATE Stock s SET s.availableStock = s.availableStock + ?1 where s.product.id = ?2 AND s.storeLocation.id = ?3")
    @Modifying(clearAutomatically = true)
    @Transactional
    Integer updateStockByProductAndLocation(Integer availableStock, Integer productId, Integer locationId);

    @Query("UPDATE Stock s SET s.availableStock = s.availableStock - ?1 where s.product = ?2 AND s.storeLocation.id = ?3")
    @Modifying(clearAutomatically = true)
    @Transactional
    Integer decreaseStockByProductsAndLocation(Integer quantity, Product product, Integer locationId);

    Boolean existsByStoreLocationIdAndProductId(Integer locationId, Integer productId);

    Boolean existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThanEqual(Integer locationId, Integer productId, Integer number);
}
