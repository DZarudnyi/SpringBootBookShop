package com.example.books.dto.book;

public record BookSearchParametersDto(
        String[] titles,
        String[] authors,
        String[] isbns,
        String description) {
}
