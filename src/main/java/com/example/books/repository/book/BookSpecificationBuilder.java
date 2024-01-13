package com.example.books.repository.book;

import com.example.books.dto.book.BookSearchParametersDto;
import com.example.books.model.Book;
import com.example.books.repository.SpecificationBuilder;
import com.example.books.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private static final String AUTHOR_KEY = "author";
    private static final String TITLE_KEY = "title";
    private static final String ISBN_KEY = "isbn";
    private static final String DESCRIPTION_KEY = "description";

    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> spec = Specification.where(null);

        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider(AUTHOR_KEY)
                    .getSpecification(searchParameters.authors()));
        }
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider(TITLE_KEY)
                    .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.isbns() != null && searchParameters.isbns().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider(ISBN_KEY)
                    .getSpecification(searchParameters.isbns()));
        }
        if (searchParameters.description() != null && !searchParameters.description().isEmpty()) {
            spec = spec.and(bookSpecificationProviderManager
                    .getSpecificationProvider(DESCRIPTION_KEY)
                    .getSpecification(searchParameters.description()));
        }

        return spec;
    }
}
