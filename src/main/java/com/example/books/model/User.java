package com.example.books.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    @NotBlank
    String email;
    @NotBlank
    String password;
    @Column(name = "first_name")
    @NotBlank
    String firstName;
    @Column(name = "last_name")
    @NotBlank
    String lastName;
    @Column(name = "shipping_address")
    String shippingAddress;
}
