package com.samnart.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samnart.ecommerce.model.User;
import com.samnart.ecommerce.payload.AddressDTO;
import com.samnart.ecommerce.service.AddressService;
import com.samnart.ecommerce.util.AuthUtil;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    AuthUtil authUtil;
    
    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@RequestParam AddressDTO addressDTO) {
        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, user);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }
}
