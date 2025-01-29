package com.samnart.social.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samnart.social.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
}
