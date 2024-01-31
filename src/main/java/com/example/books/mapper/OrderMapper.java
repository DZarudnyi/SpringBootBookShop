package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.order.OrderDto;
import com.example.books.model.Order;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
