package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.book.BookDto;
import com.example.books.dto.book.CreateBookRequestDto;
import com.example.books.dto.book.UpdateBookRequestDto;
import com.example.books.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    Book toModel(UpdateBookRequestDto updateDto);
}
