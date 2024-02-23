package com.example.books.repository.book;

import com.example.books.model.Book;
import com.example.books.model.Category;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = {
        "classpath:database/clear_scripts/delete-from-cart-items.sql",
        "classpath:database/clear_scripts/delete-from-books-categories.sql",
        "classpath:database/clear_scripts/delete-from-books.sql",
        "classpath:database/clear_scripts/delete-from-categories.sql",
        "classpath:database/books/insert-testing-book.sql",
        "classpath:database/books/insert-testing-category.sql",
        "classpath:database/books/insert-testing-books-categories.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/books/remove-testing-book-category.sql",
        "classpath:database/books/remove-testing-category.sql",
        "classpath:database/books/remove-testing-book.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void findAllBooksWithCategoriesPaged_WithValidPage_ShouldReturnListOfOneBook() {
        List<Book> actual = bookRepository
                .findAllBooksWithCategoriesPaged(PageRequest.of(0, 10));

        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals("Title", actual.get(0).getTitle());

        Set<Category> actualCategories = actual.get(0).getCategories();
        Iterator<Category> iterator = actualCategories.iterator();

        Assertions.assertEquals("Category", iterator.next().getName());
    }

    @Test
    void findIdsByPage_ValidPage_ShouldGetOneId() {
        List<Long> actual = bookRepository.findIdsByPage(PageRequest.of(0, 10));

        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(1L, actual.get(0));
    }

    @Test
    void findByIdsIn_ValidId_ShouldReturnListOfOneBook() {
        List<Book> actual = bookRepository.findByIdsIn(List.of(1L));

        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(1L, actual.get(0).getId());
    }

    @Test
    void findAllByCategoryId() {
        List<Book> actual = bookRepository.findAllByCategoryId(1L);

        Assertions.assertEquals("Title", actual.get(0).getTitle());
    }
}
