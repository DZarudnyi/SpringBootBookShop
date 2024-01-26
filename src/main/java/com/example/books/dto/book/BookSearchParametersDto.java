package com.example.books.dto.book;

import java.util.Set;

public record BookSearchParametersDto(
        String[] titles,
        String[] authors,
        String[] isbns,
        String description,
        Set<Long> categoryIds) {
}
