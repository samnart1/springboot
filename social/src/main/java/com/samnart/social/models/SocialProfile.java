package com.samnart.social.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "social_user")
    @JsonIgnore
    private SocialUser socialUser;

    private String description;

    public void setSocialProfile(SocialUser socialUser) {
        this.socialUser = socialUser;
        if (socialUser.getSocialProfile() != this) {
            socialUser.setSocialProfile(this);
        }
    }
}