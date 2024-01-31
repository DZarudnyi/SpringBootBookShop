package com.example.books.mapper;

import com.example.books.dto.orderitems.OrderItemsDto;
import com.example.books.model.CartItem;
import com.example.books.model.OrderItems;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

public interface OrderItemsMapper {
    OrderItemsDto toDto(OrderItems orderItems);

    OrderItems fromCartItemToOrderItem(CartItem cartItem);

    @AfterMapping
    default void setOrderId(
            @MappingTarget OrderItemsDto orderItemsDto,
            OrderItems orderItems
    ) {
        if (orderItems.getOrder() == null) {
            return;
        }
        orderItemsDto.setOrderId(orderItems.getOrder().getId());
    }

    @AfterMapping
    default void setBookId(
            @MappingTarget OrderItemsDto orderItemsDto,
            OrderItems orderItems
    ) {
        if (orderItems.getBook() == null) {
            return;
        }
        orderItemsDto.setBookId(orderItems.getBook().getId());
    }
}
