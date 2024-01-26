package com.example.books.service.book;

import com.example.books.dto.book.BookDto;
import com.example.books.dto.book.BookDtoWithoutCategoryIds;
import com.example.books.dto.book.BookSearchParametersDto;
import com.example.books.dto.book.CreateBookRequestDto;
import com.example.books.dto.book.UpdateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void updateById(Long id, UpdateBookRequestDto bookWithoutId);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParametersDto params);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long id);
}
