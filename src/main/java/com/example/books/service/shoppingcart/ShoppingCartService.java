package com.example.books.service.shoppingcart;

import com.example.books.dto.cartitem.CartItemDto;
import com.example.books.dto.cartitem.UpdateCartItemRequestDto;
import com.example.books.dto.shoppingcart.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto addBookToShoppingCart(CartItemDto cartItemDto);

    ShoppingCartDto getShoppingCart();

    void updateCartItemQuantity(Long itemId, UpdateCartItemRequestDto requestDto);

    void deleteCartItem(Long id);
}
