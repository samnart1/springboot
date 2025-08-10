package com.samnart.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samnart.catalog.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
