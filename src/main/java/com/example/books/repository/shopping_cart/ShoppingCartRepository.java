package com.example.books.repository.shopping_cart;

import com.example.books.dto.shopping_cart.ShoppingCartDto;
import com.example.books.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc LEFT JOIN FETCH sc.cartItems WHERE sc.user.id = (:id)")
    ShoppingCartDto getShoppingCartByUserId(Long id);
}
