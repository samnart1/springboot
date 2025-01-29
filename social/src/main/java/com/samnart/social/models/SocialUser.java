package com.samnart.social.models;

import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "socialUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // @JoinColumn(name = "social_profile_id")
    private SocialProfile socialProfile;

    @OneToMany(mappedBy = "socialUser", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_group",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "social_group_id")
    )
    private Set<SocialGroup> socialGroups = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setSocialProfile(SocialProfile socialProfile) {
        socialProfile.setSocialUser(this);
        this.socialProfile = socialProfile;
    }
    
}
