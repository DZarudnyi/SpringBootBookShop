package com.example.books.model;

import com.example.books.util.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@SQLDelete(sql = "UPDATE order SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "order")
public class Order {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;
    @Getter
    @Column(nullable = false)
    Status status;
    @Getter
    @Column(nullable = false)
    BigDecimal total;
    @Getter
    @Column(nullable = false)
    LocalDateTime orderDate;
    @Getter
    @Column(nullable = false)
    String shippingAddress;
    @Getter
    @OneToMany(mappedBy = "order")
    Set<OrderItems> orderItems;
    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted = false;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setOrderItems(Set<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
