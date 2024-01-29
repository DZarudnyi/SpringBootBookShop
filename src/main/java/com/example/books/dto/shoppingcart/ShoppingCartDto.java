package com.example.books.dto.shoppingcart;

import java.util.Set;

public record ShoppingCartDto(
        Long id,
        Long userId,
        Set<Long> cartItemsIds
) {
}
