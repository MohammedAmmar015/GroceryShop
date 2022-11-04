package com.ideas2it.groceryshop.helper;

import com.ideas2it.groceryshop.model.User;
import com.ideas2it.groceryshop.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  This Class is used to help other classes
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 */
public class UserHelper {

    @Autowired
    UserRepo userRepo;

    /**
     *  It is used to get user by id
     *
     * @return user
     */
    public User findUserById(Integer id){
        User user = userRepo.findUserByIsActiveAndId(true, id);
        return user;
    }
}
