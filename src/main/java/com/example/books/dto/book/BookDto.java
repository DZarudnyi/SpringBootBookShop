package com.example.books.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;
import org.hibernate.validator.constraints.ISBN;

public record BookDto(
        Long id,
        @NotBlank
        String title,
        @NotBlank
        String author,
        @NotNull
        @ISBN(type = ISBN.Type.ANY)
        String isbn,
        @NotNull
        @Positive
        BigDecimal price,
        String description,
        String coverImage,
        Set<Long> categoryIds
) {
}
