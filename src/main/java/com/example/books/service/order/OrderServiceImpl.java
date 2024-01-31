package com.example.books.service.order;

import com.example.books.dto.order.OrderDto;
import com.example.books.dto.order.PlaceOrderRequestDto;
import com.example.books.dto.order.SetOrderStatusRequestDto;
import com.example.books.dto.orderitems.OrderItemsDto;
import com.example.books.dto.shoppingcart.ShoppingCartDto;
import com.example.books.mapper.OrderItemsMapper;
import com.example.books.mapper.OrderMapper;
import com.example.books.mapper.ShoppingCartMapper;
import com.example.books.model.Order;
import com.example.books.model.ShoppingCart;
import com.example.books.model.User;
import com.example.books.repository.order.OrderRepository;
import com.example.books.repository.orderitems.OrderItemsRepository;
import com.example.books.repository.shoppingcart.ShoppingCartRepository;
import com.example.books.service.shoppingcart.ShoppingCartService;
import com.example.books.util.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    OrderItemsRepository orderItemsRepository;
    OrderItemsMapper orderItemsMapper;
    ShoppingCartRepository shoppingCartRepository;

    @Override
    public OrderDto placeOrder(PlaceOrderRequestDto requestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ShoppingCart shoppingCart = shoppingCartRepository.getShoppingCartByUserId(user.getId());
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setShippingAddress(requestDto.shippingAddress());
        order.setStatus(Status.PENDING);
        order.setOrderItems(
                shoppingCart.getCartItems().stream()
                        .map(orderItemsMapper::fromCartItemToOrderItem)
                        .collect(Collectors.toSet()));
        return orderMapper.toDto(orderRepository.save(order));
    }

    public List<OrderDto> getOrderHistory() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderRepository.getOrderHistoryByUserId(user.getId()).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    public OrderDto setOrderStatus(Long id, SetOrderStatusRequestDto requestDto) {
        Order orderById = orderRepository.getReferenceById(id);
        orderById.setStatus(Enum.valueOf(Status.class, requestDto.status()));
        return orderMapper.toDto(orderById);
    }

    @Override
    public List<OrderItemsDto> getOrderItemsFromOrder(Long orderId) {
        return orderItemsRepository.getOrderItemsByOrderId(orderId).stream()
                .map(orderItemsMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemsDto getOrderItemById(Long orderId, Long itemId) {
        return orderItemsMapper.toDto(
                orderItemsRepository.getOrderItemsByOrderIdAndItemId(orderId, itemId));
    }
}
