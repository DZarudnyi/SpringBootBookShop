package com.example.books.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCartItemRequestDto(
        @NotNull
        @Min(1)
        int quantity
) {
}
