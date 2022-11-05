package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Role;
import com.ideas2it.groceryshop.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQueries;
import javax.transaction.Transactional;
import java.util.List;

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
     * Find user by id
     *
     * @param isActive
     * @param id
     * @return
     */
    User findByIsActiveAndId(Boolean isActive, Integer id);

    List<User> findByIsActive(Boolean isActive);

    List<User> findByIsActiveAndRole(Boolean isActive, Role role);

    @Modifying
    @Transactional
    @Query("update User set isActive = false where id = ?1")
    void deactivateUser(Integer id);
}
