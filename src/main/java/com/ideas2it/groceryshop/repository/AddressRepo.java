package com.ideas2it.groceryshop.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ideas2it.groceryshop.model.Address;

/**
 *
 * It is used to communicate Address model with database
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {


    /**
     * It is used to find active user address
     *
     * @param isActive it checks if user is active
     * @param id it is id of user
     * @return it returns list of address
     */
    List<Address> findByIsActiveAndUserId(Boolean isActive, Integer id);

    /**
     * It is used to make user inactive
     *
     * @param id it is used to deactivate address by id
     */
    @Modifying
    @Transactional
    @Query("update Address set isActive = false where id = ?1")
    void deactivateAddress(Integer id);
}
