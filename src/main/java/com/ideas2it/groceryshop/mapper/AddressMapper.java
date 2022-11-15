package com.ideas2it.groceryshop.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressUpdateRequestDto;
import com.ideas2it.groceryshop.model.Address;

/**
 *
 * It is used to convert dto into model and vice versa
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
public class AddressMapper {

    /**
     * It is used to convert address dto into address object
     *
     * @param addressRequestDto it contains address detail
     * @return address it contains address detail
     */
    public static Address addressDtoToAddress
    (AddressRequestDto addressRequestDto) {
        Address address = new Address();
        address.setStreet(addressRequestDto.getStreet());
        address.setArea(addressRequestDto.getArea());
        address.setPinCode(addressRequestDto.getPinCode());
        address.setLandMark(addressRequestDto.getLandMark());
        return address;
    }

    /**
     * It is used to convert address object into address response Dto
     *
     * @param address it contains address detail
     * @return addressResponseDto it contains address detail
     */
    public static AddressResponseDto addressResponseDto(Address address) {
        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setId(address.getId());
        addressResponseDto.setStreet(address.getStreet());
        addressResponseDto.setArea(address.getArea());
        addressResponseDto.setPinCode(address.getPinCode());
        addressResponseDto.setLandMark(address.getLandMark());
        addressResponseDto.setCreatedAt(address.getCreatedAt());
        addressResponseDto.setModifiedAt(address.getModifiedAt());
        addressResponseDto.setModifiedBy(address.getModifiedBy());
        addressResponseDto.setIsActive(address.getIsActive());
        addressResponseDto.setIsDefault(address.getIsDefault());
        return addressResponseDto;
    }

    /**
     * It converts list of Address object into list of address response Dto
     *
     * @param addressList It contains list of address object
     * @return responseDtoList it contains list of address response Dto
     */
    public static List<AddressResponseDto> addressResponseDtoList
    (List<Address> addressList) {
        List<AddressResponseDto> responseDtoList = new ArrayList<AddressResponseDto>();
        for(Address address : addressList) {
            responseDtoList.add(addressResponseDto(address));
        }
        return responseDtoList;
    }
}