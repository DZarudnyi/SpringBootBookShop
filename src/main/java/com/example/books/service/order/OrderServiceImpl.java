package com.example.books.service.order;

import com.example.books.dto.order.OrderDto;
import com.example.books.dto.order.PlaceOrderRequestDto;
import com.example.books.dto.order.SetOrderStatusRequestDto;
import com.example.books.dto.orderitems.OrderItemResponseDto;
import com.example.books.mapper.OrderItemMapper;
import com.example.books.mapper.OrderMapper;
import com.example.books.model.CartItem;
import com.example.books.model.Order;
import com.example.books.model.OrderItem;
import com.example.books.model.ShoppingCart;
import com.example.books.model.User;
import com.example.books.repository.order.OrderRepository;
import com.example.books.repository.orderitems.OrderItemsRepository;
import com.example.books.repository.shoppingcart.ShoppingCartRepository;
import com.example.books.util.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemsRepository orderItemsRepository;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public OrderDto placeOrder(PlaceOrderRequestDto requestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setShippingAddress(requestDto.shippingAddress());
        order.setStatus(Status.PENDING);
        Set<OrderItem> orderItemSet = new HashSet<>();
        BigDecimal orderItemsTotalSum = new BigDecimal(0);
        ShoppingCart shoppingCart = shoppingCartRepository.getShoppingCartByUserId(user.getId());
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            orderItemsTotalSum = orderItemsTotalSum.add(cartItem.getBook().getPrice());
        }
        order.setTotal(orderItemsTotalSum);
        orderRepository.save(order);

        for (CartItem cartItem : shoppingCart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setBook(cartItem.getBook());
            orderItem.setPrice(cartItem.getBook().getPrice());

            orderItemsRepository.save(orderItem);
            orderItemSet.add(orderItem);
        }
        order.setOrderItems(orderItemSet);

        return orderMapper.toDto(order);
    }

    public List<OrderDto> getOrderHistory() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderRepository.getOrderHistoryByUserId(user.getId()).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderDto setOrderStatus(Long id, SetOrderStatusRequestDto requestDto) {
        Order orderById = orderRepository.getReferenceById(id);
        orderById.setStatus(Status.valueOf(requestDto.status()));
        return orderMapper.toDto(orderById);
    }

    @Override
    @Transactional
    public List<OrderItemResponseDto> getOrderItemsFromOrder(Long orderId) {
        return orderItemsRepository.getOrderItemsByOrderId(orderId).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderItemResponseDto getOrderItemById(Long orderId, Long itemId) {
        return orderItemMapper.toDto(
                orderItemsRepository.getOrderItemsByOrderIdAndItemId(orderId, itemId));
    }
}
