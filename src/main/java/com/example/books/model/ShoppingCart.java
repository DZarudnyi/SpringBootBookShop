package com.example.books.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@SQLDelete(sql = "UPDATE cart_items SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Getter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "shoppingCart")
    private Set<CartItem> cartItems;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
