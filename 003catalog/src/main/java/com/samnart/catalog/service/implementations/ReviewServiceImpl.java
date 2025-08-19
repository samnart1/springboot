package com.samnart.catalog.service.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samnart.catalog.dto.ReviewDTO;
import com.samnart.catalog.entity.Product;
import com.samnart.catalog.entity.Review;
import com.samnart.catalog.exceptions.ResourceNotFoundException;
import com.samnart.catalog.repository.ProductRepository;
import com.samnart.catalog.repository.ReviewRepository;
import com.samnart.catalog.service.interfaces.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ProductRepository productRepo;
    private final ReviewRepository reviewRepo;

    public ReviewServiceImpl(ProductRepository productRepo, ReviewRepository reviewRepo) {
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepo.findByProductIdOrderByCreatedAtDesc(productId);
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));

        return convertToDTO(review);
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO, Long productId) {
        Product product = productRepo.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        Review review = new Review();
        review.setReviewerName(reviewDTO.getReviewerName());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setProduct(product);

        Review savedReview = reviewRepo.save(review);
        return convertToDTO(savedReview);
    }

    @Override
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review review = reviewRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));

        Product product = productRepo.findById(reviewDTO.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found witho id: " + reviewDTO.getProductId()));

        review.setReviewerName(reviewDTO.getReviewerName());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setProduct(product);

        Review savedReview = reviewRepo.save(review);
        return convertToDTO(savedReview);
    }

    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));

        reviewRepo.delete(review);
    }

    public ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setReviewerName(review.getReviewerName());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setProductId(review.getProduct().getId());
        return dto;
    }
    
}
