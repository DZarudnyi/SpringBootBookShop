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
@Sql(scripts = {
        "classpath:database/clear_scripts/delete-from-users-roles.sql",
        "classpath:database/clear_scripts/delete-from-users.sql",
        "classpath:database/clear_scripts/delete-from-cart-items.sql",
        "classpath:database/clear_scripts/delete-from-shopping-carts.sql",
        "classpath:database/shoppingcart/insert-testing-user.sql",
        "classpath:database/shoppingcart/insert-testing-shopping-cart.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/shoppingcart/remove-testing-shopping-cart.sql",
        "classpath:database/shoppingcart/remove-testing-user.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ShoppingCartRepositoryTest {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    void getShoppingCartByUserId_WithValidId_ShouldReturnShoppingCart() {
        ShoppingCart actual = shoppingCartRepository.getShoppingCartByUserId(1L);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.getId());
    }

    @Test
    void getShoppingCartByUserName_WithValidUsername_ShouldReturnShoppingCart() {
        ShoppingCart actual = shoppingCartRepository.getShoppingCartByUserName("user");
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.getId());
    }
}
