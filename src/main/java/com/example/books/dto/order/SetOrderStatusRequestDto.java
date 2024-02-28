package com.example.books.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SetOrderStatusRequestDto(
        @NotNull
        @Positive
        int status
) {
}
