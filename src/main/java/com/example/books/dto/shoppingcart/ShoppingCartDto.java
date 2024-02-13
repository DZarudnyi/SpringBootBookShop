package com.example.books.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShoppingCartDto {
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Set<Long> cartItemsIds;
}
