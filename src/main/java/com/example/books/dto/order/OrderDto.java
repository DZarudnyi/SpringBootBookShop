package com.example.books.dto.order;

import com.example.books.dto.orderitems.OrderItemsDto;
import com.example.books.util.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrderDto {
    @NotNull
    Long id;
    @NotNull
    Long userId;
    List<OrderItemsDto> orderItemsDtoList;
    @NotNull
    LocalDateTime orderDate;
    @NotNull
    @Min(0)
    BigDecimal total;
    @NotNull
    Status status;
}
