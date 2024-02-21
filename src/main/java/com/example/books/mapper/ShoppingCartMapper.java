package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.shoppingcart.ShoppingCartDto;
import com.example.books.model.CartItem;
import com.example.books.model.ShoppingCart;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    ShoppingCart toEntity(ShoppingCartDto shoppingCartDto);

    @AfterMapping
    default ShoppingCartDto setCartItemsIds(
            @MappingTarget ShoppingCartDto shoppingCartDto,
            ShoppingCart shoppingCart
    ) {
        if (shoppingCart.getCartItems() == null) {
            return shoppingCartDto;
        }
        return new ShoppingCartDto(
                shoppingCartDto.id(),
                shoppingCartDto.userId(),
                shoppingCart.getCartItems().stream()
                        .map(CartItem::getId)
                        .collect(Collectors.toSet())
        );
    }
}
