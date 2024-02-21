package com.example.books.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SetOrderStatusRequestDto(
        @NotNull
        @Min(1)
        int status
) {
}
