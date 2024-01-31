package com.example.books.service.order;

import com.example.books.dto.order.OrderDto;
import com.example.books.dto.order.PlaceOrderRequestDto;
import com.example.books.dto.order.SetOrderStatusRequestDto;
import com.example.books.dto.orderitems.OrderItemResponseDto;
import java.util.List;

public interface OrderService {
    OrderDto placeOrder(PlaceOrderRequestDto requestDto);

    List<OrderDto> getOrderHistory();

    OrderDto setOrderStatus(Long id, SetOrderStatusRequestDto requestDto);

    List<OrderItemResponseDto> getOrderItemsFromOrder(Long orderId);

    OrderItemResponseDto getOrderItemById(Long orderId, Long itemId);
}
