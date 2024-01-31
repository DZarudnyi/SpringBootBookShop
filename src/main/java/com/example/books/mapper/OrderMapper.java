package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.order.OrderDto;
import com.example.books.dto.orderitems.OrderItemResponseDto;
import com.example.books.model.Order;
import com.example.books.model.OrderItem;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderItemResponseDtos", source = "orderItems")
    OrderDto toDto(Order order);

    @AfterMapping
    default void setUserId(@MappingTarget OrderDto orderDto, Order order) {
        if (order.getUser() != null) {
            orderDto.setUserId(order.getUser().getId());
        }
    }

    default Set<OrderItemResponseDto>
            mapListOfOrderItemsToOrderItemResponseDto(Set<OrderItem> orderItems) {
        if (orderItems != null) {
            return orderItems.stream()
                    .map(orderItem -> {
                        OrderItemResponseDto responseDto = new OrderItemResponseDto();
                        responseDto.setQuantity(orderItem.getQuantity());
                        responseDto.setBookId(orderItem.getBook().getId());
                        responseDto.setId(orderItem.getId());
                        return responseDto;
                    })
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
}
