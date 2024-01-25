package com.example.books.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NamedNativeQuery(
        name = "findAllBooksWithCategories",
        query =
                "SELECT \n"
                        + "\tb.id,\n"
                        + "    b.title,\n"
                        + "    b.author,\n"
                        + "    b.isbn,\n"
                        + "    b.price,\n"
                        + "    b.description,\n"
                        + "    b.cover_image,\n"
                        + "    b.is_deleted,\n"
                        + "    c.name category_name,\n"
                        + "    c.is_deleted\n"
                        + "FROM books b\n"
                        + "LEFT JOIN (\n"
                        + "books_categories bc JOIN categories c ON c.id = bc.category_id\n"
                        + ") ON b.id = bc.book_id;",
        resultSetMapping = "findAllBooksWithCategoriesMapping"
)
@SqlResultSetMapping(
        name = "findAllBooksWithCategoriesMapping",
        entities = {
                @EntityResult(
                        entityClass = Book.class,
                        fields = {
                                @FieldResult(name = "id", column = "b.id"),
                                @FieldResult(name = "title", column = "b.title"),
                                @FieldResult(name = "author", column = "b.author"),
                                @FieldResult(name = "isbn", column = "b.isbn"),
                                @FieldResult(name = "price", column = "b.price"),
                                @FieldResult(name = "description", column = "b.description"),
                                @FieldResult(name = "coverImage", column = "b.cover_image"),
                                @FieldResult(name = "categories", column = "c.name"),
                                @FieldResult(name = "isDeleted", column = "b.is_deleted")
                        }
                ),
                @EntityResult(
                        entityClass = Category.class,
                        fields = {
                                @FieldResult(name = "id", column = "c.id"),
                                @FieldResult(name = "name", column = "c.name"),
                                @FieldResult(name = "isDeleted", column = "c.is_deleted")
                        }
                )
        }
)
@Data
@Entity
@SQLDelete(sql = "UPDATE books SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "is_deleted = FALSE")
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    @Column(unique = true)
    private String isbn;
    private BigDecimal price;
    private String description;
    private String coverImage;
    @ManyToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinTable(
            name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
