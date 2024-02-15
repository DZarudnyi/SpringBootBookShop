package com.example.books.repository.shoppingcart;

import com.example.books.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc "
            + "LEFT JOIN FETCH sc.cartItems ci "
            + "LEFT JOIN FETCH ci.book cib "
            + "WHERE sc.user.id = (:id)")
    ShoppingCart getShoppingCartByUserId(Long id);

    @Query("SELECT sc FROM ShoppingCart sc "
            + "LEFT JOIN FETCH sc.cartItems ci "
            + "LEFT JOIN FETCH ci.book cib "
            + "WHERE sc.user.email = (:email)")
    ShoppingCart getShoppingCartByUserName(String email);
}
