package com.ideas2it.groceryshop.service;

import java.util.List;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressRequestDto;

/**
 *
 * It is interface of AddressService implements
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
public interface AddressService {

    /**
     * it used to add address using user it
     *
     * @param id it is used to add address to user
     * @param addressRequestDto it contains address details
     */
    void addAddress(Integer id, AddressRequestDto addressRequestDto);

    /**
     *  It is used to retrieve list of user address by user id;
     *
     * @param id it is used to get all address a user have
     * @return addressResponseDtoList it is contains list of user address
     */
    List<AddressResponseDto> getAddressesByUserId(Integer id);

    /**
     * It is used to delete user address by id
     *
     * @param id it is id to be deleted
     */
    void deleteAddressById(Integer id);
}