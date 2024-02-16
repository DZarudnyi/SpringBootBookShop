package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.cartitem.CartItemDto;
import com.example.books.model.Book;
import com.example.books.model.CartItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface CartItemMapper {
    CartItem toEntity(CartItemDto cartItemDto);

    CartItemDto toDto(CartItem cartItem);

    @AfterMapping
    default void mapBookIdToBook(@MappingTarget CartItem cartItem, CartItemDto cartItemDto) {
        if (cartItemDto.bookId() == null) {
            return;
        }
        Book book = new Book();
        book.setId(cartItemDto.bookId());
        cartItem.setBook(book);
    }

    @AfterMapping
    default CartItemDto mapBookToBookId(@MappingTarget CartItemDto cartItemDto, CartItem cartItem) {
        if (cartItem.getBook() == null) {
            return cartItemDto;
        }
        return new CartItemDto(cartItem.getBook().getId(), cartItemDto.quantity());
    }
}
