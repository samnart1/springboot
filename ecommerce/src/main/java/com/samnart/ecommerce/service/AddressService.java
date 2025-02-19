package com.samnart.ecommerce.service;

import java.util.List;

import com.samnart.ecommerce.model.User;
import com.samnart.ecommerce.payload.AddressDTO;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAddresses();

    AddressDTO getAddressById(Long addressId);

    List<AddressDTO> getUserAddresses(User user);

    AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO);

    String deleteAddress(Long addressId);
    
}
