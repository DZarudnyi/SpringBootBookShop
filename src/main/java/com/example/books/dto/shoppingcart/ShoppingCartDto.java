package com.example.books.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record ShoppingCartDto(
        Long id,
        @NotNull
        Long userId,
        @NotNull
        Set<Long> cartItemsIds
) {
}
