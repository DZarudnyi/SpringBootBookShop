package com.example.books.service.order;

import com.example.books.dto.order.OrderDto;
import com.example.books.dto.order.PlaceOrderRequestDto;
import com.example.books.dto.order.SetOrderStatusRequestDto;
import com.example.books.dto.orderitems.OrderItemsDto;

import java.util.List;

public interface OrderService {
    OrderDto placeOrder(PlaceOrderRequestDto requestDto);
    List<OrderDto> getOrderHistory();
    OrderDto setOrderStatus(Long id, SetOrderStatusRequestDto requestDto);
    List<OrderItemsDto> getOrderItemsFromOrder(Long orderId);

    OrderItemsDto getOrderItemById(Long orderId, Long itemId);
}
