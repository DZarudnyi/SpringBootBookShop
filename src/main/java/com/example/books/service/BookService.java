package com.example.books.service;

import com.example.books.dto.BookDto;
import com.example.books.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto updateById(Long id, BookDto bookDto);

    void deleteById(Long id);
}
