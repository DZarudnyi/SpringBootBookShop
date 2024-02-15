package com.example.books.repository.shoppingcart;

import com.example.books.model.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartRepositoryTest {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    @Sql(scripts = "classpath:database/shoppingcart/insert-testing-shopping-cart.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/shoppingcart/remove-testing-shopping-cart.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getShoppingCartByUserId_WithValidId_ShouldReturnShoppingCart() {
        ShoppingCart actual = shoppingCartRepository.getShoppingCartByUserId(1L);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.getId());
    }

    @Test
    @Sql(scripts = {
            "classpath:database/shoppingcart/insert-testing-user.sql",
            "classpath:database/shoppingcart/insert-testing-shopping-cart.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shoppingcart/remove-testing-shopping-cart.sql",
            "classpath:database/shoppingcart/remove-testing-user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getShoppingCartByUserName_WithValidUsername_ShouldReturnShoppingCart() {
        ShoppingCart actual = shoppingCartRepository.getShoppingCartByUserName("user");
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.getId());
    }
}
