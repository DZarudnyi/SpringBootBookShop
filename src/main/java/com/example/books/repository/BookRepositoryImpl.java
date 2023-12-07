package com.example.books.repository;

import com.example.books.model.Book;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    private RepositoryOperations<Book> repositoryOperations;

    @Autowired
    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book save(Book book) {
        return repositoryOperations.add(book);
    }

    @Override
    public List<Book> findAll() {
        return repositoryOperations.getAll("from Book", Book.class);
    }
}
