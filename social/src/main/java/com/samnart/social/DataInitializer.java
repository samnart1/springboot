package com.samnart.social;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.samnart.social.models.Post;
import com.samnart.social.models.SocialGroup;
import com.samnart.social.models.SocialProfile;
import com.samnart.social.models.SocialUser;
import com.samnart.social.repositories.PostRepository;
import com.samnart.social.repositories.SocialGroupRepository;
import com.samnart.social.repositories.SocialProfileRepository;
import com.samnart.social.repositories.SocialUserRepository;

@Configuration
public class DataInitializer {

    private final SocialUserRepository userRepository;
    private final SocialGroupRepository groupRepository;
    private final SocialProfileRepository socialProfileRepository;
    private final PostRepository postRepository;

    public DataInitializer(SocialUserRepository userRepository, SocialGroupRepository groupRepository, SocialProfileRepository socialProfileRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.socialProfileRepository = socialProfileRepository;
        this.postRepository = postRepository;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Create some users
            SocialUser user1 = new SocialUser();
            SocialUser user2 = new SocialUser();
            SocialUser user3 = new SocialUser();

            // Save users to the database
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            // Create some groups
            SocialGroup group1 = new SocialGroup();
            SocialGroup group2 = new SocialGroup();

            // Add users to groups
            group1.getSocialUsers().add(user1);
            group1.getSocialUsers().add(user2);

            group2.getSocialUsers().add(user2);
            group2.getSocialUsers().add(user3);

            // save groups to the database
            groupRepository.save(group1);
            groupRepository.save(group2);

            // Associate users with groups
            user1.getSocialGroups().add(group1);
            user2.getSocialGroups().add(group1);
            user2.getSocialGroups().add(group2);
            user3.getSocialGroups().add(group2);

            // Save users back to database to update association
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            // create some posts
            Post post1 = new Post();
            Post post2 = new Post();
            Post post3 = new Post();

            // Associate posts with users
            post1.setSocialUser(user1);
            post2.setSocialUser(user2);
            post3.setSocialUser(user3);

            // save posts to database (assuming there's a PostRepository)
            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);

            // create some social profiles
            SocialProfile profile1 = new SocialProfile();
            SocialProfile profile2 = new SocialProfile();
            SocialProfile profile3 = new SocialProfile();

            // associate profiles with users
            profile1.setSocialUser(user1);
            profile2.setSocialUser(user2);
            profile3.setSocialUser(user3);
            
            // save profiles to the database (assuming there's a socialProfileRepository)
            socialProfileRepository.save(profile1);
            socialProfileRepository.save(profile2);
            socialProfileRepository.save(profile3);

            // FETCH TYPES
            System.out.println("FETCHING SOCIAL USER");
            userRepository.findById(1l);
        };
    }

    
}
