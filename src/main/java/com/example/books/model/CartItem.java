package com.example.books.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@SQLDelete(sql = "UPDATE cart_items SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "cart_items")
public class CartItem {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private ShoppingCart shoppingCart;
    @Getter
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @Getter
    private int quantity;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public void setId(Long id) {
        this.id = id;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
