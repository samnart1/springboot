package com.samnart.ecommerce.service;

import com.samnart.ecommerce.payload.CartDTO;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);
    
}
