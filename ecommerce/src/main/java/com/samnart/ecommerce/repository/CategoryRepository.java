// package com.samnart.ecommerce.repository;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import com.samnart.ecommerce.model.Category;

// @Repository
// public interface CategoryRepository extends JpaRepository<Category, Long> {}



package com.samnart.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samnart.ecommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName);
}