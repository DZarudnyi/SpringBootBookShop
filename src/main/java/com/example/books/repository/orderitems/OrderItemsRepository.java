package com.example.books.repository.orderitems;

import com.example.books.model.OrderItems;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
    @Query("SELECT oi FROM OrderItems oi "
            + "LEFT JOIN FETCH oi.book "
            + "WHERE oi.order.id = (:orderId)")
    List<OrderItems> getOrderItemsByOrderId(Long orderId);

    @Query("SELECT oi FROM OrderItems oi "
            + "LEFT JOIN FETCH oi.book "
            + "WHERE oi.order.id = (:orderId) "
            + "AND oi.id = (:itemId)")
    OrderItems getOrderItemsByOrderIdAndItemId(Long orderId, Long itemId);
}
