/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ideas2it.groceryshop.model.User;

/**
 * <p>
 *     Providing service for storing and retrieving data
 *     from database for User entity.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * <p>
     *     Find user by id and isActive.
     * </p>
     *
     * @param isActive - To check user is active.
     * @param id - To find user.
     * @return user - Contains user details.
     */
    Optional<User> findByIsActiveAndId(Boolean isActive, Integer id);

    /**
     * <p>
     *       Get all active users.
     * </p>
     *
     * @param isActive - To check active or inactive user.
     * @return - list of active users.
     */
    List<User> findByIsActive(Boolean isActive);

    /**
     * </p>
     *      Find user by Role.
     * </p>
     *
     * @param isActive - To check active or inactive user.
     * @param name - To get by role name.
     * @return users - list of user.
     */
    List<User> findByIsActiveAndRoleName(Boolean isActive, String name);

    /**
     * <p>
     *     Make user inactive.
     * </p>
     *
     * @param id - To make user inactive.
     */
    @Modifying
    @Transactional
    @Query("update User set isActive = false where id = ?1")
    void deleteUser(Integer id);

    /**
     * <p>
     *     Check if user exist.
     * </p>
     *
     * @param userName - To find user.
     * @return boolean - If user is found true or-else false.
     */
    boolean existsByUserName(String userName);

    /**
     * <p>
     *     Find active user based on id.
     * </p>
     *
     * @param username - To get user.
     * @param isActive - To check active or inactive user.
     * @return user - Contains user details.
     */
    Optional<User> findByUserNameAndIsActive(String username, boolean isActive);

    /**
     * <p>
     *      Get user by mobile number
     * </p>
     *
     * @param mobileNumber To get username.
     * @param isActive - To check active or inactive user.
     * @return user - Contains user details.
     */
    Optional<User> findUserByMobileNumberAndIsActive(Long mobileNumber , Boolean isActive);
}