package com.example.books.controller;

import com.example.books.dto.order.OrderDto;
import com.example.books.dto.order.PlaceOrderRequestDto;
import com.example.books.dto.order.SetOrderStatusRequestDto;
import com.example.books.dto.orderitems.OrderItemResponseDto;
import com.example.books.service.order.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderDto placeOrder(@RequestBody PlaceOrderRequestDto requestDto) {
        return orderService.placeOrder(requestDto);
    }

    @GetMapping
    public List<OrderDto> getOrderHistory() {
        return orderService.getOrderHistory();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public OrderDto setOrderStatus(
            @PathVariable Long id,
            @RequestBody SetOrderStatusRequestDto requestDto
    ) {
        return orderService.setOrderStatus(id, requestDto);
    }

    @GetMapping("/{orderId}/items")
    public List<OrderItemResponseDto> getOrderItemsFromOrder(
            @PathVariable Long orderId
    ) {
        return orderService.getOrderItemsFromOrder(orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemResponseDto getOrderItemById(
            @PathVariable Long orderId,
            @PathVariable Long itemId
    ) {
        return orderService.getOrderItemById(orderId, itemId);
    }
}
