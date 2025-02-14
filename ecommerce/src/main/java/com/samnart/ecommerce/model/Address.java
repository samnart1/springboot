package com.samnart.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId; 
    
    @NotBlank
    @Size(min = 2, message = "Street name must be at least 2 characters!")
    private String street;

    @NotBlank
    @Size(min = 2, message = "Building name must be at least 2 characters!")
    private String buildingName;

    @NotBlank
    @Size(min = 2, message = "City name must be at least 2 characters!")
    private String city;

    @NotBlank
    @Size(min = 2, message = "State name must be at least 2 characters!")
    private String state;
    
    @NotBlank
    @Size(min = 2, message = "Country name must be at least 2 characters!")
    private String country;

    @NotBlank
    @Size(min = 2, message = "Pincode must be at least 2 characters!")
    private String pincode;

    @ManyToMany(mappedBy = "addresses")
    @ToString.Exclude
    private List<User> users = new ArrayList<>();
    
}


