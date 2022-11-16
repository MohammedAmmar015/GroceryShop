/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ideas2it.groceryshop.model.Role;

/**
 *
 * It is used to communicate Address model with database
 * for delete, update, create and view operations
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

    /**
     * is used to role by name
     *
     * @param isActive it is used to check if role is active
     * @param name it is used to check if role is present
     * @return it returns role object
     */
    Optional<Role> findByIsActiveAndName(Boolean isActive, String name);

    /**
     * It is used deactivate role
     *
     * @param name it is the name to be deactivated
     */
    @Modifying
    @Transactional
    @Query("update Role set isActive = false where name = ?1")
    void deactivateRole(String name);

    /**
     *  It is used to update role Name
     *
     * @param name it is updated name
     * @param nameToUpdate it is the name to be updated
     */
    @Modifying
    @Transactional
    @Query("update Role set name = ?1 where name = ?2")
    void updateRoleName(String name, String nameToUpdate);

    /**
     *
     *
     * @param name it is used to search role by name
     * @param status
     * @return Boolean it is used to check weather role is active or not
     */
    Boolean existsByNameAndIsActive(String name, Boolean status);
}