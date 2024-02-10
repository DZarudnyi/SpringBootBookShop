package com.example.books.repository.book;

import com.example.books.model.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    default List<Book> findAllBooksWithCategoriesPaged(Pageable pageable) {
        return findByIdsIn(findIdsByPage(pageable));
    }

    @Query("SELECT b.id FROM Book b")
    List<Long> findIdsByPage(Pageable pageable);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.categories WHERE b.id IN (:ids)")
    List<Book> findByIdsIn(List<Long> ids);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.categories c WHERE c.id IN (:categoryId)")
    List<Book> findAllByCategoryId(Long categoryId);
}
