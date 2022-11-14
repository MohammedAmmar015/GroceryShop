package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.AddressRequestDto;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.service.AddressService;

/**
 *
 * Address controller test is used to test view,
 * delete and create address of address controller
 *
 * @version 1.0 14-11-2022
 *
 * @author Rohit A P
 *
 */
@SpringBootTest
public class AddressControllerTest {

    @InjectMocks
    AddressController addressController = new AddressController();

    @Mock
    AddressService addressService;

    /**
     * This method is used to test the flow of
     * create address for user
     *
     * @throws NotFound
     */
    @Test
    public void createAddress() throws NotFound {
        AddressRequestDto addressRequestDto = new AddressRequestDto("Big street",
                "triplicane", 600005, "near school");
        when(addressService.addAddress(1, addressRequestDto)).thenReturn(new SuccessDto());
    }

    /**
     * This method is used to test the flow of
     * view addresses by user id
     *
     * @throws NotFound
     */
    @Test
    public void viewAddressesByUserId () throws NotFound {
        List<AddressResponseDto> addresses = new ArrayList<AddressResponseDto>();
        AddressResponseDto addressResponseDto = new AddressResponseDto(1,
                "Big street", "triplicane",600005, "near school",
                new Date(), new Date(), 1, 1, true, true);
        addresses.add(addressResponseDto);
        when(addressService.getAddressesByUserId(1)).thenReturn(addresses);
    }

    /**
     * This method is used to test the flow of delete
     * address method
     *
     * @throws NotFound it returns address not found
     */
    @Test
    public void deleteAddressById() throws NotFound {
        when(addressService.deleteAddressById(1)).thenReturn(new SuccessDto());
    }
}