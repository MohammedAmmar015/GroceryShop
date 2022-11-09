package com.ideas2it.groceryshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.AddressService;

/**
 *
 * Address class is used to view, delete and create address
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@RestController
@RequestMapping("api/v1/addresses")
public class AddressController {

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * it is used to create address
     *
     * @param id it is id of user
     * @param addressRequestDto it contains address of user
     * @throws NotFoundException user not found
     */
    @PostMapping("/{user-id}")
    public SuccessDto createAddress(@PathVariable("user-id") Integer id,
                                    @RequestBody AddressRequestDto addressRequestDto)
            throws NotFoundException {
        return addressService.addAddress(id, addressRequestDto);
    }

    /**
     * It is used to all address of a user
     *
     * @param id it is id of user
     * @return addresses list of address
     * @throws NotFoundException no address found
     */
    @GetMapping("/{user-id}")
    public List<AddressResponseDto> viewAddressesByUserId(@PathVariable("user-id") Integer id)
            throws NotFoundException {
        List<AddressResponseDto> addresses = addressService.getAddressesByUserId(id);
        return addresses;
    }

    /**
     * It used to delete address using address it
     *
     * @param id it is id of address
     * @throws NotFoundException no address found
     */
    @DeleteMapping("/{address-id}")
    public SuccessDto deleteAddressById(@PathVariable("address-id") Integer id)
            throws NotFoundException {
        return addressService.deleteAddressById(id);
    }
}