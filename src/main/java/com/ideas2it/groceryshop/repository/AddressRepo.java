package com.ideas2it.groceryshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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


    List<Address> findByIsActiveAndUserId(Boolean isActive, Integer id);

    //void saveById(Integer id, Address address);
}
