package com.example.books.service.shoppingcart;

import com.example.books.dto.cartitem.CartItemDto;
import com.example.books.dto.cartitem.UpdateCartItemRequestDto;
import com.example.books.dto.shoppingcart.ShoppingCartDto;
import com.example.books.exception.EntityNotFoundException;
import com.example.books.mapper.CartItemMapper;
import com.example.books.mapper.ShoppingCartMapper;
import com.example.books.model.CartItem;
import com.example.books.model.ShoppingCart;
import com.example.books.model.User;
import com.example.books.repository.cartitem.CartItemRepository;
import com.example.books.repository.shoppingcart.ShoppingCartRepository;
import com.example.books.repository.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserRepository userRepository;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;

    //TODO: Shopping cart should automatically be created at some point,
    // perhaps create it with a user registration

    @Override
    public ShoppingCartDto getShoppingCart() {
        String credentials = (String) SecurityContextHolder
                .getContext().getAuthentication().getCredentials();
        //TODO: create a method in user service(?) to get user by creds, to get their id
        Optional<User> user = userRepository.findByEmail(credentials);
        return shoppingCartRepository.getShoppingCartByUserId(user.orElseThrow(
                () -> new EntityNotFoundException("User doesn't have a shopping cart!")).getId());
    }

    @Override
    public ShoppingCartDto addBookToShoppingCart(CartItemDto cartItemDto) {
        CartItem cartItem = cartItemMapper.toEntity(cartItemDto);
        ShoppingCart shoppingCart = shoppingCartMapper.toEntity(getShoppingCart());
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto updateCartItemQuantity(
            Long itemId,
            UpdateCartItemRequestDto requestDto
    ) {
        CartItem cartItem = cartItemRepository.getReferenceById(itemId);
        cartItem.setQuantity(requestDto.quantity());
        return getShoppingCart();
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
