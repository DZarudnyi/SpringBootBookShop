package com.example.books.dto.book;

import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Set;

public record UpdateBookRequestDto(
        String title,
        String author,
        String isbn,
        @Min(0)
        BigDecimal price,
        String description,
        String coverImage,
        Set<Long> categoryIds
) {
}
