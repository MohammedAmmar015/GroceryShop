package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.AddressRequestDto;

import java.util.List;

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
     * it used to add address
     *
     * @param addressRequestDto
     */
    public void addAddress(AddressRequestDto addressRequestDto);

    /**
     * it is used to retrieve list of user address by user id;
     *
     * @param id
     * @return addressResponseDtoList
     */
    public List<AddressResponseDto> getAddressesByUserId(Integer id);
}