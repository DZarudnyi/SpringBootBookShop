package com.example.books.service.shopping_cart;

import com.example.books.dto.cart_item.CartItemDto;
import com.example.books.dto.cart_item.UpdateCartItemRequestDto;
import com.example.books.dto.shopping_cart.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto addBookToShoppingCart(CartItemDto cartItemDto);

    ShoppingCartDto getShoppingCart();

    ShoppingCartDto updateCartItemQuantity(Long itemId, UpdateCartItemRequestDto requestDto);

    void deleteCartItem(Long id);
}
