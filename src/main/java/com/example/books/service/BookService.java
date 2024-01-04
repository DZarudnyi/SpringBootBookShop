package com.example.books.service;

import com.example.books.dto.BookDto;
import com.example.books.dto.BookSearchParametersDto;
import com.example.books.dto.CreateBookRequestDto;
import com.example.books.dto.UpdateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void updateById(Long id, UpdateBookRequestDto bookWithoutId);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParametersDto params);
}
