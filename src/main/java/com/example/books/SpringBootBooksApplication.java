package com.example.books;

import com.example.books.model.Book;
import com.example.books.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootBooksApplication {
    @Autowired
	private BookService bookService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBooksApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			Book book = new Book();
			book.setAuthor("Mario Puso");
			book.setTitle("Godfather");
			book.setIsbn("isbn");
			book.setPrice(BigDecimal.valueOf(200));
			book.setCoverImage("image");
			book.setDescription("description");

			bookService.save(book);
			System.out.println(bookService.findAll());
		};
	}
}
