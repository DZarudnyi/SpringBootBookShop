package com.example.books.dto.order;

import com.example.books.dto.orderitems.OrderItemResponseDto;
import com.example.books.util.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDto(
        @NotNull
        Long id,
        @NotNull
        Long userId,
        Set<OrderItemResponseDto> orderItemResponseDtos,
        @NotNull
        LocalDateTime orderDate,
        @NotNull
        @Min(0)
        BigDecimal total,
        @NotNull
        Status status
) {
}
