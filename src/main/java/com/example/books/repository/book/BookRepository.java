package com.example.books.repository.book;

import com.example.books.model.Book;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Query("SELECT b FROM Book b JOIN FETCH b.categories c WHERE c.id IN (:categoryId)")
    //@EntityGraph(attributePaths = "categories")
    List<Book> findAllByCategoryId(Long categoryId);
}
