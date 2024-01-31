package com.example.books.controller;

import com.example.books.dto.order.OrderDto;
import com.example.books.dto.order.PlaceOrderRequestDto;
import com.example.books.dto.order.SetOrderStatusRequestDto;
import com.example.books.dto.orderitems.OrderItemsDto;
import com.example.books.service.order.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    OrderService orderService;

    @PostMapping
    public OrderDto placeOrder(PlaceOrderRequestDto requestDto) {
        return null;
    }

    @GetMapping
    public List<OrderDto> getOrderHistory() {
        return orderService.getOrderHistory();
    }

    @PatchMapping("/{id}")
    public OrderDto setOrderStatus(@PathVariable Long id, SetOrderStatusRequestDto requestDto) {
        return orderService.setOrderStatus(id, requestDto);
    }

    @GetMapping("/{orderId}/items")
    public List<OrderItemsDto> getOrderItemsFromOrder(@PathVariable Long orderId) {
        return orderService.getOrderItemsFromOrder(orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemsDto getOrderItemById(@PathVariable Long orderId, @PathVariable Long itemId) {
        return orderService.getOrderItemById(orderId, itemId);
    }
}
