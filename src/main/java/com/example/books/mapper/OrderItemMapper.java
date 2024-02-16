package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.orderitems.OrderItemResponseDto;
import com.example.books.model.OrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface OrderItemMapper {
    OrderItemResponseDto toDto(OrderItem orderItem);

    @AfterMapping
    default OrderItemResponseDto setBookId(
            @MappingTarget OrderItemResponseDto orderItemDto,
            OrderItem orderItem
    ) {
        if (orderItem.getBook() == null) {
            return orderItemDto;
        }
        return new OrderItemResponseDto(
                orderItemDto.id(),
                orderItem.getBook().getId(),
                orderItemDto.quantity()
        );
    }
}
