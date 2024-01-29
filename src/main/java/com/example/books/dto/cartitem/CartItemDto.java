package com.example.books.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CartItemDto(
        @NotBlank
        Long bookId,
        @NotBlank
        @Min(1)
        int quantity
) {
}
