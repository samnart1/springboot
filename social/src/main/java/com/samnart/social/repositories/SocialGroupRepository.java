package com.samnart.social.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samnart.social.models.SocialGroup;

public interface SocialGroupRepository extends JpaRepository<SocialGroup, Long> {
    
}
