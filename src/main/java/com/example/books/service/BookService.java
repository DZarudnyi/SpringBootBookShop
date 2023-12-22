package com.example.books.service;

import com.example.books.dto.BookDto;
import com.example.books.dto.BookSearchParametersDto;
import com.example.books.dto.CreateBookRequestDto;
import com.example.books.dto.UpdateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void updateById(Long id, UpdateBookRequestDto bookWithoutId);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParametersDto params);
}
