package com.ideas2it.groceryshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.dto.SuccessDto;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.helper.UserHelper;
import com.ideas2it.groceryshop.repository.AddressRepo;
import com.ideas2it.groceryshop.service.AddressService;
import com.ideas2it.groceryshop.mapper.AddressMapper;
import com.ideas2it.groceryshop.model.Address;
import com.ideas2it.groceryshop.model.User;

/**
 *
 * It is used to have user logics
 *
 * @version 1.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepo addressRepo;

    private UserHelper userHelper;

    @Autowired
    public AddressServiceImpl(AddressRepo addressRepo, UserHelper userHelper) {
        this.addressRepo = addressRepo;
        this.userHelper = userHelper;
    }

    /**
     * it used to add address to user
     *
     * @param addressRequestDto it is used to add address to user
     * @return SuccessDto it returns success message
     * @throws NotFound user not found
     */
    @Override
    public SuccessDto addAddress(Integer id, AddressRequestDto addressRequestDto)
            throws NotFound {
        Optional<User> user = userHelper.findUserById(id);
        if(user.isEmpty()) {
            throw new NotFound("User not found");
        }
        Address address = AddressMapper.addressDtoToAddress(addressRequestDto);
        address.setUser(user.get());
        addressRepo.save(address);
        return new SuccessDto(200,"Address added successfully");
    }

    /**
     *   it is used to retrieve list of user address by user id;
     *
     * @param id it is used to get all address a user have
     * @return addressResponseDtoList it is return list of address of a user
     * @throws NotFound no address found exception
     */
    @Override
    public List<AddressResponseDto> getAddressesByUserId(Integer id)
            throws NotFound {
       List<Address> address = addressRepo.findByIsActiveAndUserId(true, id);
        if(address.isEmpty()){
            throw new NotFound("Address not found");
        }
       List<AddressResponseDto> addressResponseDtoList =
               AddressMapper.addressResponseDtoList(address);
       return addressResponseDtoList;
    }

    /**
     * It is used to delete user address by id
     *
     * @param id it is id to be deleted
     * @return SuccessDto it returns success message
     * @throws NotFound address not found
     */
    @Override
    public SuccessDto deleteAddressById(Integer id) throws NotFound {
        Optional<Address> address = addressRepo.findByIsActiveAndId(true, id);
        if(address.isEmpty()) {
            throw new NotFound("Address not found");
        }
        addressRepo.deactivateAddress(id);
        return new SuccessDto(200,"Address deleted successfully");
    }
}