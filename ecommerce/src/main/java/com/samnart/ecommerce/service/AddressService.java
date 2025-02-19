package com.samnart.ecommerce.service;

import com.samnart.ecommerce.model.User;
import com.samnart.ecommerce.payload.AddressDTO;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO, User user);
    
}
