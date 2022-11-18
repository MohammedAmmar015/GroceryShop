/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.dto.AddressUpdateRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.repository.AddressRepository;
import com.ideas2it.groceryshop.service.AddressService;
import com.ideas2it.groceryshop.service.UserService;
import com.ideas2it.groceryshop.mapper.AddressMapper;
import com.ideas2it.groceryshop.model.Address;

/**
 * Address service is providing services for create, update,
 * delete and view addresses of users.
 * Data transfer objects(Dto) are converted into model object using mapper
 * for storing in database and vice versa.
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;
    private final Logger logger = LogManager.getLogger(AddressServiceImpl.class);

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository,
                              UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    /**
     * it used to add address to user
     *
     * @param addressRequestDto it is used to add address to user
     * @return SuccessResponseDto it returns success message
     * @throws NotFoundException user not found
     */
    @Override
    public SuccessResponseDto addAddress(AddressRequestDto addressRequestDto)
                                                               throws NotFoundException {
        logger.debug("Entered addAddress method");
        Address address =
                AddressMapper.addressDtoToAddress(addressRequestDto);
        List<Address> addresses =
                addressRepository.findByIsActiveAndUserId(true,
                        userService.getCurrentUser().getId());
        if(addresses.isEmpty()) {
            address.setIsDefault(Boolean.TRUE);
        } else {
            address.setIsDefault(Boolean.FALSE);
        }
        address.setUser(userService.getCurrentUser());
        addressRepository.save(address);
        logger.debug("Address added successfully");
        return new SuccessResponseDto(201,
                "Address added successfully");
    }

    /**
     * It is used to retrieve list of user address by user id;
     *
     * @return addressResponseDtoList it is return list of address
     *         of a user
     * @throws NotFoundException no address found exception
     */
    @Override
    public List<AddressResponseDto> getAddressesByUserId() throws NotFoundException {
        logger.debug("Entered getAddressesByUserId method");
        Integer userId = userService.getCurrentUser().getId();
        List<Address> address =
                addressRepository.findByIsActiveAndUserId(true, userId);
        if(address.isEmpty()){
            logger.debug("Address not found");
            throw new NotFoundException("Address not found");
        }
        List<AddressResponseDto> addressResponseDtoList =
               AddressMapper.addressResponseDtoList(address);
        logger.debug("Got list of users");
        return addressResponseDtoList;
    }

    /**
     * It is used to delete user's active address by id
     *
     * @param id it is id to be deleted
     * @return SuccessResponseDto it returns success message
     * @throws NotFoundException address not found
     */
    @Override
    public SuccessResponseDto deleteAddressById(Integer id)
                                               throws NotFoundException {
        logger.debug("Entered deleteAddressById method");
        Optional<Address> address =
                addressRepository.findByIsActiveAndId(true, id);
        if(address.isEmpty()) {
            logger.debug("Address not found");
            throw new NotFoundException("Address not found");
        }
        addressRepository.deactivateAddress(id);
        logger.debug("Address deleted successfully");
        return new SuccessResponseDto(200,"Address " +
                " deleted successfully");
    }

    /**
     * This method is used to update address by address id
     *
     * @param addressUpdateRequestDto it contains updated address details
     * @param id it is address of id
     * @return Success it contains success message
     * @throws NotFoundException it contains address not found
     */
    public SuccessResponseDto updateAddressByAddressId
            (AddressUpdateRequestDto addressUpdateRequestDto,
             Integer id) throws NotFoundException{
        logger.debug("Entered updateAddressByAddressId method");
        Optional<Address> address =
                addressRepository.findByIsActiveAndIdAndUserId(true,
                id, userService.getCurrentUser().getId());
        if(address.isEmpty()) {
            logger.debug("Address not found");
            throw new NotFoundException("Address not found");
        }
        addressRepository.updateAddressByAddressId(id,
                addressUpdateRequestDto.getStreet(),
                addressUpdateRequestDto.getArea(),
                addressUpdateRequestDto.getPinCode(),
                addressUpdateRequestDto.getLandMark(),
                addressUpdateRequestDto.getIsDefault());
        logger.debug("Address Updated successfully");
        return new SuccessResponseDto(200,
                "Address updated successfully");
    }

    /**
     * This method is used by address id
     * of active currently logged-in user
     *
     * @param id it is id of address
     * @return Address it returns address object
     */
    public Optional<Address> getAddressByAddressId(Integer id) {
        logger.debug("getAddressByAddressId");
        return addressRepository.findByIsActiveAndIdAndUserId(true, id,
                userService.getCurrentUser().getId());
    }
}