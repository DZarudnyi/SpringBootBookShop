package com.example.books.service.shoppingcart;

import com.example.books.dto.shoppingcart.ShoppingCartDto;
import com.example.books.model.ShoppingCart;
import com.example.books.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {
    private static final Long DEFAULT_ID = 1L;
    private ShoppingCart shoppingCart;
    private ShoppingCartDto shoppingCartDto;
    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @BeforeEach
    public void setup() {
        shoppingCart = new ShoppingCart();
        shoppingCart.setId(DEFAULT_ID);
        User user = new User();
        user.setId(DEFAULT_ID);
        shoppingCart.setUser(user);
        shoppingCart.set


        shoppingCartDto.setId(DEFAULT_ID);
        shoppingCartDto.setUserId(DEFAULT_ID);
        shoppingCartDto.
    }

    @Test
    void getShoppingCart() {
    }

    @Test
    void addBookToShoppingCart() {
    }

    @Test
    void updateCartItemQuantity() {
    }

    @Test
    void deleteCartItem() {
    }
}