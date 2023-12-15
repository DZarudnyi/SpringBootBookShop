package com.example.books.repository;

import com.example.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    void updateById(Long id, Book book);
}
