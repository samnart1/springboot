package com.samnart.catalog.service.implementations;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.samnart.catalog.dto.ProductCreateDTO;
import com.samnart.catalog.dto.ProductDTO;
import com.samnart.catalog.entity.Product;
import com.samnart.catalog.repository.CategoryRepository;
import com.samnart.catalog.repository.ProductRepository;
import com.samnart.catalog.repository.ReviewRepository;
import com.samnart.catalog.service.interfaces.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ReviewRepository reviewRepo;

    public ProductServiceImpl(ProductRepository productRepo, CategoryRepository categoryRepo, ReviewRepository reviewRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.reviewRepo = reviewRepo;
    }

    @Override
    public Page<ProductDTO> getAllProducts(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String keyword,
            Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllProducts'");
    }

    @Override
    public ProductDTO getProductById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductById'");
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductsByCategory'");
    }

    @Override
    public ProductDTO createProduct(ProductCreateDTO productCreateDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProduct'");
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductCreateDTO productCreateDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    }

    @Override
    public ProductDTO toggleProductStatus(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toggleProductStatus'");
    }

    @Override
    public void deleteProduct(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
    }
    
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setSku(product.getSku());
        dto.setImageUrl(product.getImageUrl());
        dto.setActive(product.getActive());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setCategoryId(product.getCategory().getId());
        dto.setCategoryName(product.getCategory().getName());

        Double averageRating = reviewRepo.findAverageRatingByProductId(product.getId());
        dto.setAverageRating(averageRating != null ? Math.round(averageRating * 10.0) / 10.0 : null);
        dto.setReviewCount(Math.toIntExact(reviewRepo.countByProductId(product.getId())));

        return dto;
    }
}
