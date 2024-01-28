package com.example.books.controller;

import com.example.books.dto.cart_item.CartItemDto;
import com.example.books.dto.cart_item.UpdateCartItemRequestDto;
import com.example.books.dto.shopping_cart.ShoppingCartDto;
import com.example.books.service.shopping_cart.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ShoppingCartDto addBookToCart(CartItemDto cartItemDto) {
        return shoppingCartService.addBookToShoppingCart(cartItemDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/cart-items/{id}")
    public ShoppingCartDto updateCartItemQuantity(
            @PathVariable Long id,
            UpdateCartItemRequestDto cartItem
    ) {
        return shoppingCartService.updateCartItemQuantity(id, cartItem);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/cart-items/{id}")
    public void deleteCartItem(@PathVariable Long id) {
        shoppingCartService.deleteCartItem(id);
    }
}
