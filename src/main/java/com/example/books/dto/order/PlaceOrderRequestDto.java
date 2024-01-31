package com.example.books.dto.order;

import jakarta.validation.constraints.NotBlank;

public record PlaceOrderRequestDto(
        @NotBlank
        String shippingAddress
) {
}
