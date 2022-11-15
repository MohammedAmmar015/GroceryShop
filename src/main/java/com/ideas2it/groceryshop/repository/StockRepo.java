package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface StockRepo extends JpaRepository<Stock, Integer> {

    /**
     * <p>
     *     This method is used to retrieve Stock by Product Id
     * </p>
     * @param productId - product's id to retrieve stock
     * @return list of stock
     */
    List<Stock> findByProductId(Integer productId);

    /**
     * <p>
     *     This method is used to retrieve stock by product and location
     * </p>
     * @param productId - product id
     * @param locationId - location id(store location)
     * @return
     */
    Stock findByProductIdAndStoreLocationId(Integer productId, Integer locationId);

    /**
     * <p>
     *     This method is used to update stock by product and location
     * </p>
     * @param stock - stock to update
     * @param productId - to which product
     * @param locationId - in which location
     * @return
     */
    @Query("UPDATE Stock s SET s.availableStock = s.availableStock + ?1 where s.product.id = ?2 AND s.storeLocation.id = ?3")
    @Modifying
    @Transactional
    Integer updateStockByProductAndLocation(Integer stock, Integer productId, Integer locationId);

    /**
     * <p>
     *     This method is used to decrease stock by order details
     * </p>
     * @param quantity - quantity to decrease
     * @param product - to which product
     * @param locationId - in which location
     * @return number of rows affected
     */
    @Query("UPDATE Stock s SET s.availableStock = s.availableStock - ?1 where s.product = ?2 AND s.storeLocation.id = ?3")
    @Modifying
    @Transactional
    Integer decreaseStockByProductsAndLocation(Integer quantity, Product product, Integer locationId);

    /**
     * <p>
     *     This method is used to increase stock based on order cancel
     * </p>
     * @param quantity - quantity to increase stock
     * @param product - to which product
     * @param locationId - in which location
     * @return number of rows affected
     */
    @Query("UPDATE Stock s SET s.availableStock = s.availableStock + ?1 where s.product = ?2 AND s.storeLocation.id = ?3")
    @Modifying
    @Transactional
    Integer increaseStockByProductsAndLocation(Integer quantity, Product product, Integer locationId);

    /**
     * <p>
     *     This method is used to check if stock is exist or not
     *     for particular product on particular location
     * </p>
     * @param locationId - in which location
     * @param productId - to which product
     * @return true if exists else false
     */
    Boolean existsByStoreLocationIdAndProductId(Integer locationId, Integer productId);

    /**
     * <p>
     *     This method is used to check if Stock is Greater than given number or not
     *     based on product and location
     * </p>
     * @param locationId - in which location
     * @param productId - for which product
     * @param number - minimum stock to check
     * @return
     */
    Boolean existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThan(Integer locationId, Integer productId, Integer number);

    /**
     * <p>
     *     This method is used to find stocks by store location id
     * </p>
     * @param id - for which locations
     * @return - list of stocks for different products
     */
    List<Stock> findByStoreLocationId(Integer id);
}
