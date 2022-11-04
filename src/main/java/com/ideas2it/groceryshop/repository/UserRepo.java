package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * It is used to communicate User model with database
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    /**
     * find user by id
     *
     * @param isActive
     * @param id
     * @return
     */
    User findUserByIsActiveAndId(Boolean isActive, Integer id);
}
