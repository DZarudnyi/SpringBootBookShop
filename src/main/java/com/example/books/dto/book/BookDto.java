package com.example.books.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class BookDto {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotNull
    @ISBN(type = ISBN.Type.ANY)
    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;
    private String description;
    private String coverImage;
}
