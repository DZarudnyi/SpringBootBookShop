package com.example.books.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.books.dto.cartitem.CartItemDto;
import com.example.books.dto.cartitem.UpdateCartItemRequestDto;
import com.example.books.dto.shoppingcart.ShoppingCartDto;
import com.example.books.model.Role;
import com.example.books.model.RoleName;
import com.example.books.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppingCartControllerTest {
    protected static MockMvc mockMvc;
    private static final Long DEFAULT_ID = 1L;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setup(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/shoppingcart/insert-testing-user.sql",
            "classpath:database/shoppingcart/insert-testing-shopping-cart.sql",
            "classpath:database/books/insert-testing-book.sql",
            "classpath:database/shoppingcart/insert-cart-item.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shoppingcart/remove-cart-item.sql",
            "classpath:database/shoppingcart/remove-testing-shopping-cart.sql",
            "classpath:database/books/remove-testing-book.sql",
            "classpath:database/shoppingcart/remove-testing-user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getShoppingCart_Ok() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/cart"))
                .andExpect(status().isOk())
                .andReturn();

        ShoppingCartDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), ShoppingCartDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(
                getShoppingCartDto(),
                actual
        );
    }

    @Test
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/shoppingcart/insert-testing-user.sql",
            "classpath:database/shoppingcart/insert-testing-shopping-cart.sql",
            "classpath:database/books/insert-testing-book.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shoppingcart/remove-testing-shopping-cart.sql",
            "classpath:database/books/remove-testing-book.sql",
            "classpath:database/shoppingcart/remove-testing-user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addBookToCart_WithValidBook_ShouldReturnShoppingCartDto() throws Exception {
        ShoppingCartDto expected = getShoppingCartDto();
        expected.setCartItemsIds(Set.of(1L, 2L));
        CartItemDto requestDto = new CartItemDto(1L, 2);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(get("/api/cart")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ShoppingCartDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), ShoppingCartDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(
                expected,
                actual
        );
    }

    @Test
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/shoppingcart/insert-testing-user.sql",
            "classpath:database/shoppingcart/insert-testing-shopping-cart.sql",
            "classpath:database/books/insert-testing-book.sql",
            "classpath:database/shoppingcart/insert-cart-item.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shoppingcart/remove-cart-item.sql",
            "classpath:database/shoppingcart/remove-testing-shopping-cart.sql",
            "classpath:database/books/remove-testing-book.sql",
            "classpath:database/shoppingcart/remove-testing-user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateCartItemQuantity_WithValidRequest_ShouldReturnCartItemDto() throws Exception {
        CartItemDto expected = new CartItemDto(1L, 1);
        UpdateCartItemRequestDto requestDto = new UpdateCartItemRequestDto(2);
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/api/cart/cart-items/1")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CartItemDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CartItemDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(
                expected,
                actual
        );
    }

    @Test
    @WithMockUser
    @Sql(scripts = {
            "classpath:database/shoppingcart/insert-testing-user.sql",
            "classpath:database/shoppingcart/insert-testing-shopping-cart.sql",
            "classpath:database/books/insert-testing-book.sql",
            "classpath:database/shoppingcart/insert-cart-item.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shoppingcart/remove-cart-item.sql",
            "classpath:database/shoppingcart/remove-testing-shopping-cart.sql",
            "classpath:database/books/remove-testing-book.sql",
            "classpath:database/shoppingcart/remove-testing-user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteCartItem_WithValidId_Ok() throws Exception {
        mockMvc.perform(delete("/api/cart/cart-items/1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    private ShoppingCartDto getShoppingCartDto() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(DEFAULT_ID);
        shoppingCartDto.setUserId(DEFAULT_ID);
        shoppingCartDto.setCartItemsIds(Set.of(DEFAULT_ID));
        return shoppingCartDto;
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
