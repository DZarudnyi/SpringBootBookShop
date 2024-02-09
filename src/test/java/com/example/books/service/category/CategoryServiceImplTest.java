package com.example.books.service.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.example.books.dto.category.CategoryDto;
import com.example.books.dto.category.CreateCategoryRequestDto;
import com.example.books.mapper.CategoryMapper;
import com.example.books.model.Category;
import com.example.books.repository.category.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    private static final Long DEFAULT_ID = 1L;

    private Category category;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setup() {
        category = new Category();
        category.setId(DEFAULT_ID);
        category.setName("Category");
        category.setDescription("Description");
    }

    @Test
    @DisplayName("findAll, passing valid Pageable page and expecting to get CategoryDto list")
    void findAll_WithValidPageable_ReturnsCategoryDtoList() {
        CategoryDto categoryDto = getCategoryDto();
        Page<Category> categoryPage = new PageImpl<>(List.of(category));

        Mockito.when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryPage);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        List<CategoryDto> expected = List.of(categoryDto);
        List<CategoryDto> actual = categoryService.findAll(mock(Pageable.class));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getById, passing valid id and expecting to get CategoryDto")
    void getById_WithValidId_ReturnsCategoryDto() {
        CategoryDto expected = getCategoryDto();

        Mockito.when(categoryRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryDto actual = categoryService.getById(DEFAULT_ID);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("save, passing valid CreateCategoryRequestDto and expecting to get CategoryDto")
    void save_ValidRequestDto_ReturnsCategoryDto() {
        CreateCategoryRequestDto requestDto = getCreateCategoryRequestDto();
        CategoryDto expected = getCategoryDto();

        Mockito.when(categoryMapper.toEntity(requestDto)).thenReturn(category);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryDto actual = categoryService.save(requestDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName(
            "update, passing valid id and CreateCategoryRequestDto, expecting to get CategoryDto"
    )
    void update_WithValidId_ReturnsCategoryDto() {
        CreateCategoryRequestDto requestDto = getCreateCategoryRequestDto();
        CategoryDto expected = getCategoryDto();

        Mockito.when(categoryMapper.toEntity(requestDto)).thenReturn(category);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryDto actual = categoryService.update(DEFAULT_ID, requestDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName(
            "deleteById, passing valid id and expecting deleteById to trigger once"
    )
    void deleteById_WithValidId_Successful() {
        categoryService.deleteById(DEFAULT_ID);
        Mockito.verify(categoryRepository, times(1)).deleteById(DEFAULT_ID);
    }

    private static CategoryDto getCategoryDto() {
        return new CategoryDto(
                DEFAULT_ID,
                "Category",
                "Description"
        );
    }

    private static CreateCategoryRequestDto getCreateCategoryRequestDto() {
        return new CreateCategoryRequestDto(
                "Category",
                "Description");
    }
}
