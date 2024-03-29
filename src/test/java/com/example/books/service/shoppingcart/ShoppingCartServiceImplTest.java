package com.example.books.service.shoppingcart;

import com.example.books.dto.cartitem.CartItemDto;
import com.example.books.dto.cartitem.UpdateCartItemRequestDto;
import com.example.books.dto.shoppingcart.ShoppingCartDto;
import com.example.books.mapper.CartItemMapper;
import com.example.books.mapper.ShoppingCartMapper;
import com.example.books.model.Book;
import com.example.books.model.CartItem;
import com.example.books.model.Role;
import com.example.books.model.RoleName;
import com.example.books.model.ShoppingCart;
import com.example.books.model.User;
import com.example.books.repository.cartitem.CartItemRepository;
import com.example.books.repository.shoppingcart.ShoppingCartRepository;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {
    private static final Long DEFAULT_ID = 1L;
    private ShoppingCart shoppingCart;
    private ShoppingCartDto shoppingCartDto;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ShoppingCartMapper shoppingCartMapper;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private CartItemMapper cartItemMapper;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @BeforeEach
    public void setup() {
        shoppingCart = new ShoppingCart();
        shoppingCart.setId(DEFAULT_ID);
        User user = getUser();
        shoppingCart.setUser(user);
        shoppingCart.setCartItems(Set.of(getCartItem()));

        shoppingCartDto = new ShoppingCartDto(
                DEFAULT_ID,
                DEFAULT_ID,
                Set.of(DEFAULT_ID)
        );
    }

    private void setupShoppingCartForUser() {
        User user = getUser();
        authentication = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );
        Mockito.doReturn(shoppingCart).when(shoppingCartRepository)
                .getShoppingCartByUserName("email");
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getShoppingCart_Ok() {
        setupShoppingCartForUser();
        Mockito.when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(shoppingCartDto);

        ShoppingCartDto actual = shoppingCartService.getShoppingCart();
        Assertions.assertNotNull(actual);

        Assertions.assertEquals(shoppingCartDto, actual);
    }

    @Test
    void addBookToShoppingCart_WithValidBook_ShouldReturnShoppingCartDto() {
        setupShoppingCartForUser();
        Mockito.when(cartItemMapper.toEntity(getCartItemDto())).thenReturn(getCartItem());
        Mockito.when(shoppingCartMapper.toEntity(shoppingCartDto)).thenReturn(shoppingCart);
        Mockito.when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(shoppingCartDto);

        ShoppingCartDto actual = shoppingCartService.addBookToShoppingCart(getCartItemDto());
        Assertions.assertNotNull(actual);

        Assertions.assertEquals(shoppingCartDto, actual);
    }

    @Test
    void updateCartItemQuantity_Ok() {
        CartItemDto expected = getCartItemDto();
        CartItem cartItem = getCartItem();

        Mockito.when(cartItemRepository.getReferenceById(DEFAULT_ID)).thenReturn(cartItem);
        Mockito.doReturn(expected).when(cartItemMapper).toDto(cartItem);

        CartItemDto actual = shoppingCartService.updateCartItemQuantity(DEFAULT_ID,
                new UpdateCartItemRequestDto(1));
        Mockito.verify(cartItemRepository, Mockito.times(1))
                .getReferenceById(DEFAULT_ID);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteCartItem_Ok() {
        shoppingCartService.deleteCartItem(DEFAULT_ID);
        Mockito.verify(cartItemRepository, Mockito.times(1))
                .deleteById(DEFAULT_ID);
    }

    private CartItemDto getCartItemDto() {
        return new CartItemDto(DEFAULT_ID, 1);
    }

    private CartItem getCartItem() {
        CartItem cartItem = new CartItem(); 
        cartItem.setId(DEFAULT_ID);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(getBook());
        cartItem.setQuantity(2);
        return cartItem;
    }

    private Book getBook() {
        Book book = new Book();
        book.setId(DEFAULT_ID);
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setIsbn("1234");
        book.setPrice(BigDecimal.valueOf(20.00));
        return book;
    }

    private User getUser() {
        User user = new User();
        user.setId(DEFAULT_ID);
        user.setEmail("email");
        user.setPassword("password");
        user.setFirstName("name");
        user.setLastName("surname");
        user.setShippingAddress("address");
        user.setRoles(Set.of(new Role(DEFAULT_ID, RoleName.ROLE_USER)));
        return user;
    }
}
