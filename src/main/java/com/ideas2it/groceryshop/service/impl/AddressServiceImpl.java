package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.helper.UserHelper;
import com.ideas2it.groceryshop.model.User;
import lombok.experimental.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.repository.AddressRepo;
import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.service.AddressService;
import com.ideas2it.groceryshop.mapper.AddressMapper;
import com.ideas2it.groceryshop.model.Address;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * It is used to have user logics
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserHelper userHelper;

    /**
     * it used to add address
     *
     * @param addressRequestDto
     */
    public void addAddress(Integer id,AddressRequestDto addressRequestDto){
        Address address = AddressMapper.addressDtoToAddress(addressRequestDto);
        User user = userHelper.findUserById(id);
        address.setUser(user);
        addressRepo.save(address);
        //addressRepo.saveById(id, address);
    }

    /**
     * it is used to retrieve list of user address by user id;
     *
     * @param id
     * @return addressResponseDtoList
     */
    public List<AddressResponseDto> getAddressesByUserId(Integer id) {
       List<Address> address = addressRepo.findByIsActiveAndUserId(true, id);
       List<AddressResponseDto> addressResponseDtoList = AddressMapper.addressResponseDtoList(address);
       return addressResponseDtoList;
    }
}