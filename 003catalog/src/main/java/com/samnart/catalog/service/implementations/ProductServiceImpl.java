package com.samnart.catalog.service.implementations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.samnart.catalog.dto.ProductCreateDTO;
import com.samnart.catalog.dto.ProductDTO;
import com.samnart.catalog.entity.Category;
import com.samnart.catalog.entity.Product;
import com.samnart.catalog.exceptions.ResourceNotFoundException;
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
                Page<Product> products = productRepo.findProductsWithFilters(categoryId, minPrice, maxPrice, keyword, pageable);
                return products.map(this::convertToDTO);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepo.findByIdWithDetails(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
            return convertToDTO(product);
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepo.findByCategoryId(categoryId);
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO createProduct(ProductCreateDTO productCreateDTO) {
        
        if (productCreateDTO.getSku() != null && productRepo.findBySkuIgnoreCase(productCreateDTO.getSku()).isPresent()) {
            throw new IllegalArgumentException("Product with SKU '" + productCreateDTO.getSku() + "' already exists!");
        }

        Category category = categoryRepo.findById(productCreateDTO.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productCreateDTO.getCategoryId()));

        Product product = new Product();
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setStockQuantity(productCreateDTO.getStockQuantity());
        product.setSku(productCreateDTO.getSku());
        product.setImageUrl(productCreateDTO.getImageUrl());
        product.setCategory(category);

        Product savedProduct = productRepo.save(product);

        return convertToDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductCreateDTO productCreateDTO) {
        Product product = productRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (productCreateDTO.getSku() != null && !productCreateDTO.getSku().equalsIgnoreCase(product.getSku())) {
            Optional<Product> existingProduct = productRepo.findBySkuIgnoreCase(productCreateDTO.getSku());
            if (existingProduct.isPresent() && !existingProduct.get().getId().equals(id)) {
                throw new IllegalArgumentException("Product already exists with Sku: " + productCreateDTO.getSku());
            }
        }

        Category category = categoryRepo.findById(productCreateDTO.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productCreateDTO.getCategoryId()));

        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setStockQuantity(productCreateDTO.getStockQuantity());
        product.setSku(productCreateDTO.getSku());
        product.setImageUrl(productCreateDTO.getImageUrl());
        product.setCategory(category);

        return convertToDTO(product);
    }

    @Override
    public ProductDTO toggleProductStatus(Long id) {
        Product product = productRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setActive(!product.getActive());
        Product savedProduct = productRepo.save(product);
        return convertToDTO(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        productRepo.delete(product);
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
