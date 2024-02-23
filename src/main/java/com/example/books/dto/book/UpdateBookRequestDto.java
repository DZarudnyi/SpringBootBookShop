package com.example.books.dto.book;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;

public record UpdateBookRequestDto(
        String title,
        String author,
        String isbn,
        @Positive
        BigDecimal price,
        String description,
        String coverImage,
        Set<Long> categoryIds
) {
}
