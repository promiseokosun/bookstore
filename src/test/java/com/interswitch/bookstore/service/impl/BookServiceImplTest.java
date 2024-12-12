package com.interswitch.bookstore.service.impl;

import com.interswitch.bookstore.BookStoreApplicationTests;
import com.interswitch.bookstore.enums.Genre;
import com.interswitch.bookstore.model.Book;
import com.interswitch.bookstore.model.Cart;
import com.interswitch.bookstore.repo.BookRepo;
import com.interswitch.bookstore.repo.CartRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class BookServiceImplTest extends BookStoreApplicationTests {

    @Autowired
    @InjectMocks
    private BookServiceImpl bookService;

    @MockBean
    private BookRepo bookRepo;

    private Long bookId = 1l;
    private String title = "Test Title";
    Book bookMock = new Book();

    @BeforeEach
    void setUp() {
        bookMock.setAuthor("Test Author");
        bookMock.setGenre(Genre.FICTION);
        bookMock.setIsbn("1002001-00");
        bookMock.setTitle(title);
        bookMock.setAvailable(true);
    }

    @Test
    void searchBooks() {
        when(bookRepo.searchBooks(title, null, null, null, Pageable.unpaged()))
                .thenReturn(new PageImpl<>(Collections.singletonList(bookMock)));

        assertThat(bookService.searchBooks(title, null, null, null, Pageable.unpaged())
                .getContent().get(0).getTitle()).isEqualTo(title);
    }

    @Test
    void findBook() {
        when(bookRepo.findById(bookId))
                .thenReturn(Optional.of(bookMock));
        assertThat(bookService.findBook(bookId)).isEqualTo(bookMock);
    }
}