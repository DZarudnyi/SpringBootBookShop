package com.example.books.dto.cart_item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCartItemRequestDto(
        @NotNull
        @Min(1)
        int quantity
) {
}
