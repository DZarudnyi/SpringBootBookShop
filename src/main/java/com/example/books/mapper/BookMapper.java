package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.BookDto;
import com.example.books.dto.CreateBookRequestDto;
import com.example.books.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    Book toModel(BookDto bookDto);
}
