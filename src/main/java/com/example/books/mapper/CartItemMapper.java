package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.cart_item.CartItemDto;
import com.example.books.model.CartItem;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface CartItemMapper {
    CartItem toEntity(CartItemDto cartItemDto);
}
