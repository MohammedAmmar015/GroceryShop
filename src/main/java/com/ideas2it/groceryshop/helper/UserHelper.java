package com.ideas2it.groceryshop.helper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.repository.UserRepo;
import com.ideas2it.groceryshop.model.User;

/**
 *  This Class is used to help other service classes
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 */
@Service
public class UserHelper {

    @Autowired
    private UserRepo userRepo;

    /**
     *  It is used to get user by id
     *
     * @return user
     */
    public Optional<User> findUserById(Integer id) {
        Optional<User> user = userRepo.findByIsActiveAndId(true, id);
        return user;
    }
}