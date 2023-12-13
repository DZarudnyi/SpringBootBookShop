package com.example.books.repository;

import com.example.books.model.Book;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final RepositoryOperations<Book> repositoryOperations;

    @Override
    public Book save(Book book) {
        return repositoryOperations.add(book);
    }

    @Override
    public List<Book> findAll() {
        return repositoryOperations.getAll("SELECT b FROM Book b", Book.class);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return repositoryOperations.findById(id, Book.class);
    }
}
