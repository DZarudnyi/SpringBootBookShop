package com.example.books.dto.order;

import com.example.books.dto.orderitems.OrderItemResponseDto;
import com.example.books.util.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderDto {
    @NotNull
    private Long id;
    @NotNull
    private Long userId;
    private Set<OrderItemResponseDto> orderItemResponseDtos;
    @NotNull
    private LocalDateTime orderDate;
    @NotNull
    @Min(0)
    private BigDecimal total;
    @NotNull
    private Status status;
}
