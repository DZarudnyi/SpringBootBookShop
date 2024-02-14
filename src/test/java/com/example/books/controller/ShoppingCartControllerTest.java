package com.example.books.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.books.dto.cartitem.CartItemDto;
import com.example.books.dto.cartitem.UpdateCartItemRequestDto;
import com.example.books.dto.shoppingcart.ShoppingCartDto;
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
    private static final Long DEFAULT_ID = 1L;
    private static MockMvc mockMvc;
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
            "classpath:database/shoppingcart/insert-testing-shopping-cart.sql",
            "classpath:database/books/insert-testing-book.sql",
            "classpath:database/shoppingcart/insert-cart-item.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shoppingcart/remove-cart-item.sql",
            "classpath:database/shoppingcart/remove-testing-shopping-cart.sql",
            "classpath:database/books/remove-testing-book.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getShoppingCart() throws Exception {
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
            "classpath:database/shoppingcart/insert-testing-shopping-cart.sql",
            "classpath:database/books/insert-testing-book.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shoppingcart/remove-testing-shopping-cart.sql",
            "classpath:database/books/remove-testing-book.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addBookToCart() throws Exception {
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
            "classpath:database/shoppingcart/insert-testing-shopping-cart.sql",
            "classpath:database/books/insert-testing-book.sql",
            "classpath:database/shoppingcart/insert-cart-item.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shoppingcart/remove-cart-item.sql",
            "classpath:database/shoppingcart/remove-testing-shopping-cart.sql",
            "classpath:database/books/remove-testing-book.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateCartItemQuantity() throws Exception {
        UpdateCartItemRequestDto requestDto = new UpdateCartItemRequestDto(2);
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/cart-items/1")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/shoppingcart/insert-testing-shopping-cart.sql",
            "classpath:database/books/insert-testing-book.sql",
            "classpath:database/shoppingcart/insert-cart-item.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shoppingcart/remove-cart-item.sql",
            "classpath:database/shoppingcart/remove-testing-shopping-cart.sql",
            "classpath:database/books/remove-testing-book.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteCartItem() throws Exception {
        MvcResult result = mockMvc.perform(get("/cart-items/1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    private ShoppingCartDto getShoppingCartDto() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(DEFAULT_ID);
        shoppingCartDto.setUserId(DEFAULT_ID);
        shoppingCartDto.setCartItemsIds(Set.of(DEFAULT_ID));
        return shoppingCartDto;
    }
}
