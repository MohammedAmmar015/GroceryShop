package com.ideas2it.groceryshop.service;

import java.util.List;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.dto.AddressUpdateRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFound;

/**
 *
 * It is interface of AddressService
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
public interface AddressService {

    /**
     * it used to add address using user id
     *
     * @param id it is used to add address to user
     * @param addressRequestDto it contains address details
     * @throws NotFound user not found
     */
    SuccessResponseDto addAddress(Integer id, AddressRequestDto addressRequestDto)
            throws NotFound;

    /**
     *  It is used to retrieve list of user address by user id;
     *
     * @param id it is used to get all address a user have
     * @return addressResponseDtoList it is contains list of user address
     * @throws NotFound no address found exception
     */
    List<AddressResponseDto> getAddressesByUserId(Integer id) throws NotFound;

    /**
     * It is used to delete user address by id
     *
     * @param id it is id to be deleted
     * @throws NotFound no address found exception
     */
    SuccessResponseDto deleteAddressById(Integer id) throws NotFound;

    /**
     * This method is used to update address by address id
     *
     * @param addressUpdateRequestDto it contains updated address details
     * @param id it is address of id
     * @return Success it contains success message
     * @throws NotFound it contains address not found
     */
    SuccessResponseDto updateAddressByAddressId
    (AddressUpdateRequestDto addressUpdateRequestDto, Integer id) throws NotFound;
}