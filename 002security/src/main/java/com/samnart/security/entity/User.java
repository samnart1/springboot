package com.samnart.entity

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(stratey = GenerationType.IDENTITY)
  private Long id; 

  
}
