package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.StoreLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StoreRepo extends JpaRepository<StoreLocation, Integer> {
    List<StoreLocation> findByIsActive(Boolean isActive);

    StoreLocation findByIsActiveAndId(Boolean isActive, Integer id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE StoreLocation SET isActive = false WHERE id = ?1")
    void deleteStoreById(Integer storeId);
}