package com.samnart.social.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samnart.social.models.SocialProfile;

public interface SocialProfileRepository extends JpaRepository<SocialProfile, Long> {
    
}
