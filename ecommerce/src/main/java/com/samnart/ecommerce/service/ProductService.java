package com.samnart.ecommerce.service;

import com.samnart.ecommerce.model.Product;
import com.samnart.ecommerce.payload.ProductDTO;

public interface ProductService {

    ProductDTO addProduct(Long categoryId, Product product);
    
}
