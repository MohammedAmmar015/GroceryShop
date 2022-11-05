package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import com.ideas2it.groceryshop.model.User;
import org.springframework.stereotype.Service;

/**
 *  This Class is used to help service classes
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
    public User findUserById(Integer id) {
        User user = userRepo.findByIsActiveAndId(true, id);
        return user;
    }
}