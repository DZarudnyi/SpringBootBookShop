package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.order.OrderDto;
import com.example.books.model.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(target = "orderItemResponseDtos", source = "orderItems")
    OrderDto toDto(Order order);

    @AfterMapping
    default OrderDto setUserId(@MappingTarget OrderDto orderDto, Order order) {
        if (order.getUser() != null) {
            return new OrderDto(
                    orderDto.id(),
                    order.getUser().getId(),
                    orderDto.orderItemResponseDtos(),
                    orderDto.orderDate(),
                    orderDto.total(),
                    orderDto.status()
            );
        }
        return orderDto;
    }
}
