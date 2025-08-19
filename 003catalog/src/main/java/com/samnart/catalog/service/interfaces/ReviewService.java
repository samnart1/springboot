package com.samnart.catalog.service.interfaces;

import java.util.List;

import com.samnart.catalog.dto.ReviewDTO;

public interface ReviewService {
    List<ReviewDTO> getReviewsByProductId(Long productId);
    ReviewDTO getReviewById(Long id);
    ReviewDTO createReview(ReviewDTO reviewDTO, Long productId);
    ReviewDTO updateReview(Long id, ReviewDTO reviewDTO);
    void deleteReview(Long id);
}
