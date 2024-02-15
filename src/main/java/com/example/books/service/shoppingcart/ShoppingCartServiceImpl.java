package com.example.books.service.shoppingcart;

import com.example.books.dto.cartitem.CartItemDto;
import com.example.books.dto.cartitem.UpdateCartItemRequestDto;
import com.example.books.dto.shoppingcart.ShoppingCartDto;
import com.example.books.mapper.CartItemMapper;
import com.example.books.mapper.ShoppingCartMapper;
import com.example.books.model.CartItem;
import com.example.books.model.ShoppingCart;
import com.example.books.repository.cartitem.CartItemRepository;
import com.example.books.repository.shoppingcart.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartDto getShoppingCart() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String userName = loggedInUser.getName();
        ShoppingCart shoppingCart = shoppingCartRepository.getShoppingCartByUserName(userName);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto addBookToShoppingCart(CartItemDto cartItemDto) {
        CartItem cartItem = cartItemMapper.toEntity(cartItemDto);
        ShoppingCart shoppingCart = shoppingCartMapper.toEntity(getShoppingCart());
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public CartItemDto updateCartItemQuantity(
            Long itemId,
            UpdateCartItemRequestDto requestDto
    ) {
        CartItem cartItem = cartItemRepository.getReferenceById(itemId);
        cartItem.setQuantity(requestDto.quantity());
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
