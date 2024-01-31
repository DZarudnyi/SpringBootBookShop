package com.example.books.dto.orderitems;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemsDto {
    Long id;
    Long orderId;
    Long bookId;
    int quantity;
    BigDecimal price;
}
