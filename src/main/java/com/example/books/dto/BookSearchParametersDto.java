package com.example.books.dto;

public record BookSearchParametersDto(
        String[] titles,
        String[] authors,
        String[] isbns,
        String description) {
}
