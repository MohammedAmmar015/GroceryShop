package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.model.Address;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * It is used to convert dto into model and vice versa
 *
 * @version 19.0 04-11-2022
 *
 * @author Rohit A P
 *
 */
public class AddressMapper {

    public static Address addressDtoToAddress(AddressRequestDto addressRequestDto) {
        Address address = new Address();
        address.setStreet(addressRequestDto.getStreet());
        address.setArea(addressRequestDto.getArea());
        address.setPinCode(addressRequestDto.getPinCode());
        address.setLandMark(addressRequestDto.getLandMark());
        return address;
    }

    public static AddressResponseDto addressResponseDto(Address address) {
        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setId(address.getId());
        addressResponseDto.setStreet(address.getStreet());
        addressResponseDto.setArea(address.getArea());
        addressResponseDto.setPinCode(address.getPinCode());
        addressResponseDto.setLandMark(addressResponseDto.getLandMark());
        addressResponseDto.setCreatedAt(addressResponseDto.getCreatedAt());
        addressResponseDto.setModifiedAt(address.getModifiedAt());
        addressResponseDto.setModifiedBy(address.getModifiedBy());
        addressResponseDto.setIsActive(address.getIsActive());
        addressResponseDto.setIsDefault(address.getIsDefault());
        return addressResponseDto;
    }

    public static List<AddressResponseDto> addressResponseDtoList(List<Address> addressList) {
        List<AddressResponseDto> responseDtoList = new ArrayList<AddressResponseDto>();
        for(Address address : addressList) {
            responseDtoList.add(addressResponseDto(address));
        }
        return responseDtoList;
    }
}