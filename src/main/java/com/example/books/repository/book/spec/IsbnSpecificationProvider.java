package com.example.books.repository.book.spec;

import com.example.books.model.Book;
import com.example.books.repository.SpecificationProvider;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "isbn";
    }
}
