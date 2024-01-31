package com.example.books.dto.orderitems;

import lombok.Data;

@Data
public class OrderItemResponseDto {
    private Long id;
    private Long bookId;
    private int quantity;
}
