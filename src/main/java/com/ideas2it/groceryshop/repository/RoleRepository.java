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
 * <p>
 *     Providing service for storing and retrieving data
 *     from database for Role entity.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * <p>
     *     Find role by name.
     * </p>
     *
     * @param isActive - To check active or inactive role.
     * @param name - To get role.
     * @return - role object.
     */
    Optional<Role> findByIsActiveAndName(Boolean isActive, String name);

    /**
     * <p>
     *     Update role Name by role name.
     * </p>
     *
     * @param name - To update name.
     * @param nameToUpdate - Name to be updated.
     */
    @Modifying
    @Transactional
    @Query("update Role set name = ?1 where name = ?2")
    void updateRoleName(String name, String nameToUpdate);

    /**
     * <p>
     *     Check if role exist.
     * </p>
     *
     * @param name - To check role is exists.
     * @param isActive - To check active or inactive role.
     * @return Boolean - If exists true or-else false.
     */
    Boolean existsByNameAndIsActive(String name, Boolean isActive);
}