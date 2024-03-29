package com.example.books.controller;

import com.example.books.dto.cartitem.CartItemDto;
import com.example.books.dto.cartitem.UpdateCartItemRequestDto;
import com.example.books.dto.shoppingcart.ShoppingCartDto;
import com.example.books.service.shoppingcart.ShoppingCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartDto addBookToCart(
            @RequestBody @Valid CartItemDto cartItemDto
    ) {
        return shoppingCartService.addBookToShoppingCart(cartItemDto);
    }

    @PutMapping("/cart-items/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CartItemDto updateCartItemQuantity(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCartItemRequestDto cartItem
    ) {
        return shoppingCartService.updateCartItemQuantity(id, cartItem);
    }

    @DeleteMapping("/cart-items/{id}")
    public void deleteCartItem(@PathVariable Long id) {
        shoppingCartService.deleteCartItem(id);
    }
}
