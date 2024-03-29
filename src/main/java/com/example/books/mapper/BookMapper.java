package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.book.BookDto;
import com.example.books.dto.book.BookDtoWithoutCategoryIds;
import com.example.books.dto.book.CreateBookRequestDto;
import com.example.books.dto.book.UpdateBookRequestDto;
import com.example.books.model.Book;
import com.example.books.model.Category;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "categories", source = "categoryIds")
    Book toModel(CreateBookRequestDto requestDto);

    Book toModel(UpdateBookRequestDto updateDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default BookDto setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() == null) {
            return bookDto;
        }
        return new BookDto(
                bookDto.id(),
                bookDto.title(),
                bookDto.author(),
                bookDto.isbn(),
                bookDto.price(),
                bookDto.description(),
                bookDto.coverImage(),
                book.getCategories().stream()
                        .map(Category::getId)
                        .collect(Collectors.toSet())
        );
    }

    default Set<Category> mapSetOfIdsToCategoriesSet(Set<Long> ids) {
        if (ids == null) {
            return new HashSet<Category>();
        }
        return ids.stream()
                .map(id -> {
                    Category category = new Category();
                    category.setId(id);
                    return category;
                })
                .collect(Collectors.toSet());
    }
}
