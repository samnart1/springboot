package com.samnart.entity

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

  @Id
  @GeneratedValue(stratey = GenerationType.IDENTITY)
  private Long id; 

  private String username;
  
  private String email;

  private String password;

  private String firstName;

  private String lastName;

  private Role role = Role.User;

  private boolean enabled = true;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }
}
