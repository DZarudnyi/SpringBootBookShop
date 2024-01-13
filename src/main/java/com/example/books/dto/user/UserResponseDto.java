package com.example.books.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserResponseDto {
    @NotBlank
    Long id;
    @Email
    @NotBlank
    String email;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    String shippingAddress;
}
