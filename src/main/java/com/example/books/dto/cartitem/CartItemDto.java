package com.example.books.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDto {
    @NotNull
    private Long bookId;
    @NotNull
    @Min(1)
    private int quantity;

    public CartItemDto(Long bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}
