package com.interswitch.bookstore.model;

import com.interswitch.bookstore.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_order")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity<Long> {
    private Long userId;
    @Column(unique = true)
    private String paymentReference;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_book_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();

    public void setBooks(List<Book> bookList) {
        books.addAll(bookList);
    }
}

