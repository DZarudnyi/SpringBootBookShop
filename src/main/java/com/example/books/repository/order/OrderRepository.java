package com.example.books.repository.order;

import com.example.books.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.user "
            + "LEFT JOIN FETCH o.orderItems WHERE o.user.id = (:id)")
    List<Order> getOrderHistoryByUserId(Long id);
}
