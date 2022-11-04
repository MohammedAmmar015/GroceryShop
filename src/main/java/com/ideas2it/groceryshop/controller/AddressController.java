package com.ideas2it.groceryshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.service.AddressService;

import java.util.List;

/**
 *
 * It is used to do CRUD operations
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
    @PostMapping("/")
    public void createAddress(@PathVariable Integer id,
                              @RequestBody AddressRequestDto addressRequestDto) {
        addressService.addAddress(addressRequestDto);
    }

    @GetMapping("/")
    public void viewAddressesByUserId(@RequestParam Integer id) {
        List<AddressResponseDto> addresses = addressService.getAddressesByUserId(id);
    }
}
