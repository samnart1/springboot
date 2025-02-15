package com.samnart.ecommerce.service;

import org.springframework.stereotype.Service;

import com.samnart.ecommerce.payload.CartDTO;

@Service
public class CartServiceImpl implements CartService{

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        // find existing cart or create one!
        // retrieve the product details
        // perform validations
        // create cart item
        // save cart item
        return null;
    }
    
}
