package com.example.books.dto.shopping_cart;

import com.example.books.model.CartItem;
import java.util.Set;

public record ShoppingCartDto (
        Long id,
        Long userId,
        Set<Long> cartItemsIds
) {
}
