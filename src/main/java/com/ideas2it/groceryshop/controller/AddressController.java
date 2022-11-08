package com.ideas2it.groceryshop.controller;

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
import com.ideas2it.groceryshop.service.AddressService;

import java.util.List;

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

    @Autowired
    private AddressService addressService;

    /**
     * it is used to create address
     *
     * @param addressRequestDto
     */
    @PostMapping("/{user-id}")
    public void createAddress(@PathVariable("user-id") Integer id,
                              @RequestBody AddressRequestDto addressRequestDto) {
        addressService.addAddress(id, addressRequestDto);
    }

    /**
     * It is used to all address of a user
     *
     * @param id it is id of user
     * @return addresses list of address
     */
    @GetMapping("/{user-id}")
    public List<AddressResponseDto> viewAddressesByUserId(@PathVariable("user-id") Integer id) {
        List<AddressResponseDto> addresses = addressService.getAddressesByUserId(id);
        return addresses;
    }

    /**
     * It used to delete address using address it
     *
     * @param id it is id of address
     */
    @DeleteMapping("/{address-id}")
    public void deleteAddressById(@PathVariable("address-id") Integer id){
        addressService.deleteAddressById(id);
    }
}
