package com.ideas2it.groceryshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.dto.AddressUpdateRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFound;
import com.ideas2it.groceryshop.helper.UserHelper;
import com.ideas2it.groceryshop.repository.AddressRepo;
import com.ideas2it.groceryshop.service.AddressService;
import com.ideas2it.groceryshop.mapper.AddressMapper;
import com.ideas2it.groceryshop.model.Address;
import com.ideas2it.groceryshop.model.User;

/**
 * Address service is used to create, update,
 * delete and view addresses of users.
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
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
     * @return SuccessResponseDto it returns success message
     * @throws NotFound user not found
     */
    @Override
    public SuccessResponseDto addAddress(Integer id,
                                 AddressRequestDto addressRequestDto)
            throws NotFound {
        Optional<User> user = userHelper.findUserById(id);
        if(user.isEmpty()) {
            throw new NotFound("User not found");
        }
        Address address =
                AddressMapper.addressDtoToAddress(addressRequestDto);
        List<Address> addresses =
                addressRepo.findByIsActiveAndUserId(true, id);
        if(addresses.isEmpty()) {
            address.setIsDefault(Boolean.TRUE);
        } else {
            address.setIsDefault(Boolean.FALSE);
        }
        address.setUser(user.get());
        addressRepo.save(address);
        return new SuccessResponseDto(200,"Address added successfully");
    }

    /**
     *   it is used to retrieve list of user address by user id;
     *
     * @param id it is used to get all address a user have
     * @return addressResponseDtoList it is return list of address
     *         of a user
     * @throws NotFound no address found exception
     */
    @Override
    public List<AddressResponseDto> getAddressesByUserId(Integer id)
            throws NotFound {
       List<Address> address =
               addressRepo.findByIsActiveAndUserId(true, id);
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
     * @return SuccessResponseDto it returns success message
     * @throws NotFound address not found
     */
    @Override
    public SuccessResponseDto deleteAddressById(Integer id) throws NotFound {
        SuccessResponseDto SuccessResponseDto;
        Optional<Address> address = addressRepo.findByIsActiveAndId(true, id);
        if(address.isEmpty()) {
            throw new NotFound("Address not found");
        }
        addressRepo.deactivateAddress(id);
        SuccessResponseDto = new SuccessResponseDto(200,"Address " +
                    " deleted successfully");
        return SuccessResponseDto;
    }

    /**
     * This method is used to update address by address id
     *
     * @param addressUpdateRequestDto it contains updated address details
     * @param id it is address of id
     * @return Success it contains success message
     * @throws NotFound it contains address not found
     */
    public SuccessResponseDto updateAddressByAddressId
            (AddressUpdateRequestDto addressUpdateRequestDto,
             Integer id) throws NotFound{
        addressRepo.updateAddressByAddressId(id,
                addressUpdateRequestDto.getStreet(),
                addressUpdateRequestDto.getArea(),
                addressUpdateRequestDto.getPinCode(),
                addressUpdateRequestDto.getLandMark(),
                addressUpdateRequestDto.getIsDefault());
        return new SuccessResponseDto(200,"Address updated successfully");
    }
}