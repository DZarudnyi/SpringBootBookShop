package com.example.books.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import org.hibernate.validator.constraints.ISBN;

public record CreateBookRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String author,
        @NotNull
        @ISBN(type = ISBN.Type.ANY)
        String isbn,
        @NotNull
        @Min(0)
        BigDecimal price,
        String description,
        String coverImage,
        Set<Long> categoryIds
) {
}
