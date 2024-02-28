package com.example.books.dto.cartitem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemDto(
        @NotNull
        Long bookId,
        @NotNull
        @Positive
        int quantity
) {
}
