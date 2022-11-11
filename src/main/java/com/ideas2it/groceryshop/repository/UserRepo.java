package com.ideas2it.groceryshop.repository;

import java.util.List;
import java.util.Optional;

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
 * @version 1.0 04-11-2022
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
     */
    Optional<User> findByIsActiveAndId(Boolean isActive, Integer id);

    /**
     *  It is used to get all active users
     *
     * @param isActive it checks for active user
     * @return it returns list of active users
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

    /**
     *
     *
     * @param userName it is used to find user by username
     * @return boolean it returns user exist or not
     */
    boolean existsByUserName(String userName);

    /**
     * It is used to find active user based on id
     *
     * @param username it is username
     * @param isActive weather use is active or not
     * @return user it returns user object
     */
    Optional<User> findByUserNameAndIsActive(String username, boolean isActive);

    /**
     * This method is used to get user by mobile number
     *
     * @param mobileNumber it is user mobile number
     * @param isActive weather use is active or not
     * @return user it returns user object
     */
    Optional<User> findUserByMobileNumberAndIsActive(Long mobileNumber , boolean isActive);
}
