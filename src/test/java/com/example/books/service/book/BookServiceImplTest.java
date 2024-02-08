package com.example.books.service.book;

import com.example.books.dto.book.BookDto;
import com.example.books.dto.book.BookDtoWithoutCategoryIds;
import com.example.books.dto.book.CreateBookRequestDto;
import com.example.books.dto.book.UpdateBookRequestDto;
import com.example.books.exception.EntityNotFoundException;
import com.example.books.mapper.BookMapper;
import com.example.books.model.Book;
import com.example.books.repository.book.BookRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.example.books.repository.category.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)//potentially change to @RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {
    private static final Long DEFAULT_ID = 1L;
    private static final BigDecimal DEFAULT_PRICE = BigDecimal.valueOf(20.00);

    private Book book;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void setup() {
        book = new Book();
        book.setId(DEFAULT_ID);
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setIsbn("1234");
        book.setPrice(DEFAULT_PRICE);
    }

    @Test
    @DisplayName("save, passing valid book, expecting to get BookDto")
    public void save_ValidRequestDto_ReturnsBookDto() {
        BookDto bookDto = getBookDto();

        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Title");
        requestDto.setAuthor("Author");
        requestDto.setIsbn("1234");
        requestDto.setPrice(DEFAULT_PRICE);

        Mockito.when(bookRepository.save(book)).thenReturn(book);
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);
        Mockito.when(bookMapper.toModel(requestDto)).thenReturn(book);

        BookDto expected = bookDto;
        BookDto actual = bookService.save(requestDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("findAll, expecting to get list of BookDto")
    public void findAll_ValidPageable_ReturnsBooksList(){
        BookDto bookDto = getBookDto();

        List<BookDto> expected = List.of(bookDto);

        Mockito.when(bookRepository.findAllBooksWithCategoriesPaged(any(Pageable.class))).thenReturn(List.of(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        List<BookDto> actual = bookService.findAll(mock(Pageable.class));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("findById, passing valid id and expecting to get BookDto")
    public void findById_ValidId_ReturnsBookDto() {
        Long bookId = DEFAULT_ID;

        BookDto bookDto = getBookDto();

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto expected = bookDto;
        BookDto actual = bookService.findById(bookId);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("updateById, passing valid id and verifying number of method calls")
    public void updateById_ValidId_ReturnsValidBook() {
        UpdateBookRequestDto requestDto = new UpdateBookRequestDto();
        requestDto.setTitle("Title");
        requestDto.setAuthor("Author");
        requestDto.setIsbn("1234");
        requestDto.setPrice(DEFAULT_PRICE);

        Mockito.when(bookMapper.toModel(requestDto)).thenReturn(book);

        bookService.updateById(DEFAULT_ID, requestDto);
        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }

    @Test
    @DisplayName("deleteById, expecting method to be called once")
    public void deleteById_WithCorrectId_Successful() {
        Long bookId = DEFAULT_ID;
        bookService.deleteById(bookId);
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(bookId);
    }

    @Test
    @DisplayName("findAllByCategoryId, check if exception is thrown if id is invalid")
    public void
    findAllByCategoryId_WithInvalidCategoryId_ThrowsEntityNotFoundExceptionException() {
        Long categoryId = 100L;

        Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> bookService.findAllByCategoryId(categoryId)
        );

        String expected = "There is no category with id = 100";
        String actual = exception.getMessage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("findAllByCategoryId, verify that correct book returns when category id exists")
    public void findAllByCategoryId_WithValidCategoryId_ReturnsValidBook() {
        Long categoryId = DEFAULT_ID;

        BookDtoWithoutCategoryIds expectedBook = new BookDtoWithoutCategoryIds(
                DEFAULT_ID,
                "Title",
                "Author",
                "1234",
                DEFAULT_PRICE,
                "",
                "");

        Mockito.when(bookRepository.findAllByCategoryId(categoryId)).thenReturn(List.of(book));
        Mockito.when(categoryRepository.existsById(categoryId)).thenReturn(true);
        Mockito.when(bookMapper.toDtoWithoutCategories(any(Book.class))).thenReturn(expectedBook);

        List<BookDtoWithoutCategoryIds> actual = bookService.findAllByCategoryId(categoryId);

        List<BookDtoWithoutCategoryIds> expected = List.of(expectedBook);
        Assertions.assertEquals(expected, actual);
    }

    private static BookDto getBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(DEFAULT_ID);
        bookDto.setTitle("Title");
        bookDto.setAuthor("Author");
        bookDto.setIsbn("1234");
        bookDto.setPrice(DEFAULT_PRICE);
        return bookDto;
    }
}
