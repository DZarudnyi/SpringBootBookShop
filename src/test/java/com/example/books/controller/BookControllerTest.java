package com.example.books.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.books.dto.book.BookDto;
import com.example.books.dto.book.CreateBookRequestDto;
import com.example.books.dto.book.UpdateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
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

@Sql(scripts = {
        "classpath:database/clear_scripts/delete-from-cart-items.sql",
        "classpath:database/clear_scripts/delete-from-books-categories.sql",
        "classpath:database/clear_scripts/delete-from-books.sql",
        "classpath:database/clear_scripts/delete-from-categories.sql",
        "classpath:database/books/insert-controller-testing-book.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/books/delete-controller-testing-book.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    @WithMockUser
    void getAll_Ok() throws Exception {
        List<BookDto> expected = List.of(getBookDto());

        MvcResult result = mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), BookDto[].class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(
                expected,
                actual,
                "id", "description", "coverImage");
    }

    @Test
    @WithMockUser
    void getBookById_WithCorrectId_Ok() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/books/123"))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), BookDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals("Title1", actual.title());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create new book")
    @Sql(scripts = "classpath:database/books/delete-book-with-title.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createBook_ValidBook_Successful() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                "Title1",
                "Author1",
                "978-0-545-01022-1",
                BigDecimal.valueOf(20.00),
                "",
                "",
                null
        );

        BookDto expected = getBookDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post("/api/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(
                        expected,
                        actual,
                        "id", "description", "coverImage", "categoryIds"
        );
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateBook_WithValidRequest_Ok() throws Exception {
        UpdateBookRequestDto requestDto = new UpdateBookRequestDto(
                "Title2",
                "Author2",
                "1234",
                BigDecimal.valueOf(25.00),
                "",
                "",
                null
        );

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(put("/api/books/123")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        MvcResult result = mockMvc.perform(get("/api/books/123"))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("Title2", actual.title());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteBook_ValidId_Ok() throws Exception {
        mockMvc.perform(delete("/api/books/123"))
                .andExpect(status().isOk())
                .andReturn();
    }

    private static BookDto getBookDto() {
        return new BookDto(
                1L,
                "Title",
                "Author",
                "978-0-545-01022-1",
                BigDecimal.valueOf(20.00),
                "",
                "",
                null
        );
    }
}
