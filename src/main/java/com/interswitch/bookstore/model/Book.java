package com.interswitch.bookstore.model;

import com.interswitch.bookstore.enums.Genre;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity
@Table(name = "tbl_book")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity<Long> {
    private String title;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private String isbn;
    private String author;
    private Integer yearOfPublication;
    private boolean isAvailable;
}

