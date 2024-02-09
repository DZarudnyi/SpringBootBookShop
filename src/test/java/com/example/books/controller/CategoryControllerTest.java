package com.example.books.controller;

import com.example.books.dto.book.BookDto;
import com.example.books.dto.book.BookDtoWithoutCategoryIds;
import com.example.books.dto.category.CategoryDto;
import com.example.books.dto.category.CreateCategoryRequestDto;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create new category")
    @Sql(scripts = "classpath:database/categories/remove-category-with-name-and-description.sql"
            , executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createCategory_WithValidRequest_Ok() throws Exception {
        CreateCategoryRequestDto requestDto =
                new CreateCategoryRequestDto("Category1", "Description1");

        CategoryDto expected = getCategoryDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                post("/api/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @WithMockUser
    @DisplayName("Get category by ID, should return category")
    @Sql(scripts = "classpath:database/categories/insert-testing-category.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/remove-category-with-name-and-description.sql"
            , executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAll_Ok() throws Exception {
        CategoryDto expected = getCategoryDto();

        MvcResult result = mockMvc.perform(
                get("/api/categories")
        )
        .andExpect(status().isOk())
        .andReturn();

        CategoryDto[] actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), CategoryDto[].class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @WithMockUser
    @DisplayName("Get all categories, should return list of categories")
    @Sql(scripts = "classpath:database/categories/insert-testing-category.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/remove-category-with-name-and-description.sql"
            , executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getCategoryById_WithValidId_ShouldReturnCategoryDto() throws Exception {
        CategoryDto expected = getCategoryDto();

        MvcResult result = mockMvc.perform(
                get("/api/categories/1")
        )
        .andExpect(status().isOk())
        .andReturn();

        CategoryDto actual =
                objectMapper.readValue(result.getResponse().getContentAsString(), CategoryDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Update category, should return category")
    @Sql(scripts = "classpath:database/categories/insert-testing-category.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/remove-category-with-name-and-description.sql"
            , executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateCategory_WithValidRequest_ShouldReturnCategoryDto() throws Exception {
        CreateCategoryRequestDto requestDto =
                new CreateCategoryRequestDto("Category1", "Description1");

        CategoryDto expected = getCategoryDto();

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                put("/api/categories/1")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryDto.class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Update category, should return category")
    @Sql(scripts = "classpath:database/categories/insert-testing-category.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteCategory_Ok() throws Exception {
        mockMvc.perform(
                delete("/api/categories/1")
        )
        .andExpect(status().isOk())
        .andReturn();
    }

    @Test
    @WithMockUser
    @DisplayName("Get all categories, should return list of categories")
    @Sql(scripts = {"classpath:database/categories/insert-testing-category.sql",
            "classpath:database/books/insert-testing-book.sql",
            "classpath:database/books/insert-testing-books-categories.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/books/remove-testing-book-category.sql",
            "classpath:database/categories/remove-category-with-name-and-description.sql",
            "classpath:database/books/remove-testing-book.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getBooksByCategoryId_WithValidId_ShouldReturnListOfBooks() throws Exception {
        BookDtoWithoutCategoryIds bookDto = new BookDtoWithoutCategoryIds(
                1L,
                "Title",
                "Author",
                "1234",
                BigDecimal.valueOf(20.00),
                "Description",
                "");

        MvcResult result = mockMvc.perform(
                get("/api/categories/1/books")
        )
        .andExpect(status().isOk())
        .andReturn();

        BookDto[] actual = objectMapper.readValue(result.getResponse().getContentAsString(), BookDto[].class);
        Assertions.assertNotNull(actual);

        EqualsBuilder.reflectionEquals(List.of(bookDto), List.of(actual), "id");
    }

    private static CategoryDto getCategoryDto() {
        return new CategoryDto(1L, "Category1", "Description1");
    }
}