package com.example.books.dto.user;

import com.example.books.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@FieldMatch(firstString = "password", secondString = "repeatPassword")
public record UserRegistrationRequestDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Length(min = 4, max = 35)
        String password,
        @NotBlank
        @Length(min = 4, max = 35)
        String repeatPassword,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        String shippingAddress
) {
}
