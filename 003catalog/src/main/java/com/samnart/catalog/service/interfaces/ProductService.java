package com.samnart.catalog.service.interfaces;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.samnart.catalog.dto.ProductCreateDTO;
import com.samnart.catalog.dto.ProductDTO;

public interface ProductService {
    Page<ProductDTO> getAllProducts(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String keyword, Pageable pageable);
    public ProductDTO getProductById(Long id);
    public List<ProductDTO> getProductsByCategory(Long categoryId);
    public ProductDTO createProduct(ProductCreateDTO productCreateDTO);
    public ProductDTO updateProduct(Long id, ProductCreateDTO productCreateDTO);
    public ProductDTO toggleProductStatus(Long id);
    public void deleteProduct(Long id);
}
