package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.shopping_cart.ShoppingCartDto;
import com.example.books.model.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface ShoppingCartMapper {
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    ShoppingCart toEntity(ShoppingCartDto shoppingCartDto);
}
