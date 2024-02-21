package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.shoppingcart.ShoppingCartDto;
import com.example.books.model.CartItem;
import com.example.books.model.ShoppingCart;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.books.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Mapping(source = "cartItemsIds", target = "cartItems")
    @Mapping(source = "userId", target = "user")
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

    default User mapUserIdToUser(Long userId) {
        if (userId == null) {
            return new User();
        }
        User user = new User();
        user.setId(userId);
        return user;
    }

    default Set<CartItem> mapCartItemIdsToCartItems(Set<Long> ids) {
        if (ids == null) {
            return new HashSet<>();
        }
        return ids.stream()
                .map(id -> {
                    CartItem cartItem = new CartItem();
                    cartItem.setId(id);
                    return cartItem;
                })
                .collect(Collectors.toSet());
    }
}
