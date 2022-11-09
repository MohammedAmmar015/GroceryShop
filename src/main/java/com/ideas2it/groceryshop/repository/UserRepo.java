package com.ideas2it.groceryshop.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ideas2it.groceryshop.model.Role;
import com.ideas2it.groceryshop.model.User;

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
     * Find user by id and isActive
     *
     * @param isActive it is used to check user is active
     * @param id it is used to search user by id
     * @return user it returns user object
     *
     */
    User findByIsActiveAndId(Boolean isActive, Integer id);

    /**
     * Find active users
     *
     * @param isActive active users
     * @return users list of users
     */
    List<User> findByIsActive(Boolean isActive);

    /**
     * Find user by Role
     *
     * @param isActive active user
     * @param role it is role of user
     * @return users list of user
     */
    List<User> findByIsActiveAndRole(Boolean isActive, Role role);

    /**
     * It is used to make user inactive
     *
     * @param id it is the id to be deactivated
     */
    @Modifying
    @Transactional
    @Query("update User set isActive = false where id = ?1")
    void deactivateUser(Integer id);
}
