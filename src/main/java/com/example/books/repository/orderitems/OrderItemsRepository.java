package com.example.books.repository.orderitems;

import com.example.books.model.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM OrderItem oi "
            + "LEFT JOIN FETCH oi.book "
            + "WHERE oi.order.id = (:orderId)")
    List<OrderItem> getOrderItemsByOrderId(Long orderId);

    @Query("SELECT oi FROM OrderItem oi "
            + "LEFT JOIN FETCH oi.book "
            + "WHERE oi.order.id = (:orderId) "
            + "AND oi.id = (:itemId)")
    OrderItem getOrderItemsByOrderIdAndItemId(Long orderId, Long itemId);
}
