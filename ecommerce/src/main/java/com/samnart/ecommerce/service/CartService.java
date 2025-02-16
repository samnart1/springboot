package com.samnart.ecommerce.service;

import java.util.List;

import com.samnart.ecommerce.payload.CartDTO;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCart(String emailId, Long cartId);
    
}
