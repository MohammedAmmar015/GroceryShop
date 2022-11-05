package com.ideas2it.groceryshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideas2it.groceryshop.model.Role;

import java.util.Optional;

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
public interface RoleRepo extends JpaRepository<Role, Integer> {

    /**
     *
     * is used to role by name
     *
     * @param isActive
     * @param name
     * @return
     */
    Optional<Role> findByIsActiveAndName(Boolean isActive, String name);

}