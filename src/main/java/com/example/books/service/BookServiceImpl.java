package com.example.books.service;

import com.example.books.dto.BookDto;
import com.example.books.dto.CreateBookRequestDto;
import com.example.books.mapper.BookMapper;
import com.example.books.model.Book;
import com.example.books.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(requestDto)));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by id " + id)));
    }

    @Override
    public BookDto updateById(Long id, BookDto bookDto) {
        bookRepository.updateById(id, bookDto);
        return bookMapper.toDto(bookRepository.save(bookById));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
