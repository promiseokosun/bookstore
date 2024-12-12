package com.interswitch.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "tbl_cart")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cart extends BaseEntity<Long> {

    @Column(unique = true)
    private Long userId; // assuming a user to a cart.

    @OneToMany
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        if(Objects.isNull(books) || books.isEmpty()) {
            books = new ArrayList<>();

        }
         books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public void clearCart() {
        books.clear();
    }
}

