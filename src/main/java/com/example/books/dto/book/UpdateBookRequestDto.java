package com.example.books.dto.book;

import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateBookRequestDto {
    private String title;
    private String author;
    private String isbn;
    @Min(0)
    private BigDecimal price;
    private String description;
    private String coverImage;
    private Set<Long> categoryIds;
}
