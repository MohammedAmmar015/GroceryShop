/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import java.util.List;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.dto.AddressUpdateRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;

/**
 * Address service is providing services for create, update,
 * delete and view addresses of users.
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
public interface AddressService {

    /**
     * it used to add address using user id
     *
     * @param addressRequestDto it contains address details
     * @throws NotFoundException user not found
     */
    SuccessResponseDto addAddress(AddressRequestDto addressRequestDto)
            throws NotFoundException;

    /**
     *  It is used to retrieve list of user address by user id;
     *
     * @return addressResponseDtoList it is contains list of user address
     * @throws NotFoundException no address found exception
     */
    List<AddressResponseDto> getAddressesByUserId() throws NotFoundException;

    /**
     * It is used to delete user address by id
     *
     * @param id it is id to be deleted
     * @throws NotFoundException no address found exception
     */
    SuccessResponseDto deleteAddressById(Integer id) throws NotFoundException;

    /**
     * This method is used to update address by address id
     *
     * @param addressUpdateRequestDto it contains updated address details
     * @param id it is address of id
     * @return Success it contains success message
     * @throws NotFoundException it contains address not found
     */
    SuccessResponseDto updateAddressByAddressId
    (AddressUpdateRequestDto addressUpdateRequestDto, Integer id) throws NotFoundException;
}