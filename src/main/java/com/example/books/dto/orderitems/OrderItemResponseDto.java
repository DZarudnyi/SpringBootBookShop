package com.example.books.dto.orderitems;

public record OrderItemResponseDto (
        Long id,
        Long bookId,
        int quantity
) {
}
