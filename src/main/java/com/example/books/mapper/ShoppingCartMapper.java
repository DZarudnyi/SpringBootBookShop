package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.shoppingcart.ShoppingCartDto;
import com.example.books.model.CartItem;
import com.example.books.model.ShoppingCart;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface ShoppingCartMapper {
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    ShoppingCart toEntity(ShoppingCartDto shoppingCartDto);

    @AfterMapping
    default void setUserIds(
            @MappingTarget ShoppingCartDto shoppingCartDto,
            ShoppingCart shoppingCart
    ) {
        if (shoppingCart.getUser() == null) {
            return;
        }
        shoppingCartDto.setUserId(shoppingCart.getUser().getId());
    }

    @AfterMapping
    default void setCartItemsIds(
            @MappingTarget ShoppingCartDto shoppingCartDto,
            ShoppingCart shoppingCart
    ) {
        if (shoppingCart.getCartItems() == null) {
            return;
        }
        shoppingCartDto.setCartItemsIds(shoppingCart.getCartItems().stream()
                .map(CartItem::getId)
                .collect(Collectors.toSet()));
    }
}