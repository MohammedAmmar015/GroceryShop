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

import com.ideas2it.groceryshop.model.Address;

/**
 * It is used to communicate Address model with database
 * for delete, update, create and view operations
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {

    /**
     * It is used to find active user address
     *
     * @param isActive it checks if user is active
     * @param id it is id of user
     * @return it returns list of address
     */
    List<Address> findByIsActiveAndUserId(Boolean isActive, Integer id);

    /**
     * It is used to make user inactive
     *
     * @param id it is used to deactivate address by id
     * @return
     */
    @Modifying
    @Transactional
    @Query("update Address set isActive = false where id = ?1")
    Boolean deactivateAddress(Integer id);

    /**
     * It is used to find active address by address id
     *
     * @param isActive it checks for address is active
     * @param id it is id of address
     * @return Address it returns address object
     */
    Optional<Address> findByIsActiveAndId(Boolean isActive, Integer id);

    /**
     * This method is used to get address by active status, address id
     * and user id
     *
     * @param isActive it used to check address is active
     * @param addressId it is id of address
     * @param userId it is id of user
     * @return Address it returns address object
     */
    Optional<Address> findByIsActiveAndIdAndUserId(Boolean isActive,
                                                   Integer addressId, Integer userId);
    /**
     * This method is used to delete all address
     * of a user by user id
     *
     * @param id it is it of user
     * @return Boolean it returns true or false
     */
    @Modifying
    @Transactional
    @Query("Update Address a set a.isActive = false where a.user.id = ?1")
    void deactivateAddresses(Integer id);

    /**
     * This method is used to update address of user using address id.
     *
     * @param id it is id of address
     * @param street it is name of street
     * @param area it is name of area
     * @param pinCode it is pin code of area
     * @param landMark it is landmark of area
     * @param isDefault it is used to if user want to set it
     *                 as default address
     * @return Boolean it returns true or false
     */
    @Modifying
    @Transactional
    @Query("Update Address a set a.street = ?2, a.area = ?3," +
            " a.pinCode = ?4, a.landMark = ?5, a.isDefault = ?6" +
            " where a.id = ?1")
    void updateAddressByAddressId(Integer id, String street,
                                  String area, Integer pinCode,
                                  String landMark, Boolean isDefault);
}
