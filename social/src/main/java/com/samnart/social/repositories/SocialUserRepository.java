package com.samnart.social.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samnart.social.models.SocialUser;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
    
}
