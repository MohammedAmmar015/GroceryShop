/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.dto.AddressUpdateRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.helper.UserHelper;
import com.ideas2it.groceryshop.service.AddressService;

/**
 *
 * Address controller class is used to view,
 * delete and create address
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@RestController
@RequestMapping("api/v1/addresses")
public class AddressController {

    private AddressService addressService;
    private Logger logger = LogManager.getLogger(AddressController.class);
    private UserHelper userHelper;

    @Autowired
    public AddressController(AddressService addressService, UserHelper userHelper) {
        this.addressService = addressService;
        this.userHelper = userHelper;
    }

    /**
     * It is used to create address
     *
     * @param addressRequestDto it contains address of user
     * @throws NotFound user not found
     */
    @PostMapping
    public SuccessResponseDto createAddress(@Valid @RequestBody AddressRequestDto addressRequestDto) throws NotFound {
        logger.debug("Entered createAddress method");
        int id = userHelper.getCurrentUser().getId();
        return addressService.addAddress(id, addressRequestDto);
    }

    /**
     * It is used to all address of a user
     *
     * @return addresses list of address
     * @throws NotFound no address found
     */
    @GetMapping
    public List<AddressResponseDto> viewAddressesByUserId() throws NotFound {
        logger.debug("Entered viewAddressesByUserId method");
        List<AddressResponseDto> addresses = addressService.getAddressesByUserId();
        return addresses;
    }

    /**
     * It used to delete address using address id
     *
     * @param id it is id of address
     * @throws NotFound no address found
     */
    @DeleteMapping("/{addressId}")
    public SuccessResponseDto deleteAddressById
    (@Valid @PathVariable("addressId") Integer id) throws NotFound {
        logger.debug("Entered deleteAddressById method");
        return addressService.deleteAddressById(id);
    }

    /**
     * This method is used to update user address by address id and
     * update address request object
     *
     * @return SuccessResponseDto it contains success message
     * @throws NotFound it contains address not found
     */
    @PutMapping("/{addressId}")
    public SuccessResponseDto updateAddressByAddressId
    (@RequestBody AddressUpdateRequestDto addressUpdateRequestDto,
     @PathVariable("addressId") Integer id) throws NotFound {
        logger.debug("Entered UpdateAddressByAddressId method");
        return addressService.updateAddressByAddressId
                (addressUpdateRequestDto, id);
    }
}