/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.repository.AddressRepo;

/**
 *  This Class is used to help service classes
 *
 * @version 1.0
 * @author Rohit A P
 * @since 14-11-2022
 */
@Service
public class AddressHelper {

    @Autowired
    AddressRepo addressRepo;

    /**
     * This method is used to delete all address
     * of a user
     *
     * @param id it is id of user
     */
    public void deleteAllAddressByUserId(Integer id) {
        addressRepo.deactivateAddresses(id);
    }
}
