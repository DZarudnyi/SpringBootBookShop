package com.example.books.controller;

import com.example.books.dto.book.BookDto;
import com.example.books.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    protected static MockMvc mockMvc;
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
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("")
    @Sql(scripts = "")
    void getAll() {
    }

    @Test
    void getBookById() {
    }

    @Test
    void searchBooks() {
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create new book")
    @Sql(scripts = "classpath:database/books/delete-book-with-title.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createBook_ValidBook_Successful() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto()
                .setTitle("Title1")
                .setAuthor("Author1")
                .setIsbn("1234")
                .setPrice(BigDecimal.valueOf(20.00));

        BookDto expected = new BookDto()
                .setId(1L)
                .setTitle("Title1")
                .setAuthor("Author1")
                .setIsbn("1234")
                .setPrice(BigDecimal.valueOf(20.00));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                post("/api/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isCreated())
        .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual, "id", "description", "coverImage");
    }

    @Test
    void updateBook() {
    }

    @Test
    void deleteBook() {
    }
}