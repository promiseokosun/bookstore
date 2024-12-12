package com.interswitch.bookstore.repo;

import com.interswitch.bookstore.BookStoreApplicationTests;
import com.interswitch.bookstore.enums.Genre;
import com.interswitch.bookstore.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

class BookRepoTest extends BookStoreApplicationTests {

    @Autowired
    private BookRepo bookRepo;

    @BeforeEach
    void setUp() {
    }

    @Test
    void searchBooks() {
        shouldReturnEmptyWhenInvalidSearchParamIsProvided();
        shouldReturnAllBooksWhenNoSearchParamIsProvided();
        shouldReturnAllBooksWithGenre();
        shouldReturnAllBooksWithYear();
        shouldReturnAllBooksContainingAuthor();
        shouldReturnAllBooksContainingTitle();
        shouldReturnAllBooksWithMatchingSearchPair();
    }

    private void shouldReturnEmptyWhenInvalidSearchParamIsProvided() {
        Page<Book> bookPage = bookRepo.searchBooks("blah", null, null, null, Pageable.unpaged());
        assertThat(bookPage.getContent()).isEmpty();
    }

    private void shouldReturnAllBooksWhenNoSearchParamIsProvided() {
        Page<Book> bookPage = bookRepo.searchBooks(null, null, null, null, Pageable.unpaged());
        assertThat(bookPage.getContent().size()).isEqualTo(8);
    }

    private void shouldReturnAllBooksWithGenre() {
        Page<Book> bookPage = bookRepo.searchBooks(null, null, Genre.FICTION.name(), null, Pageable.unpaged());
        assertThat(bookPage.getContent().size()).isEqualTo(2);
    }

    private void shouldReturnAllBooksWithYear() {
        Page<Book> bookPage = bookRepo.searchBooks(null, null, null, 1929, Pageable.unpaged());
        assertThat(bookPage.getContent().size()).isEqualTo(3);
    }

    private void shouldReturnAllBooksContainingAuthor() {
        Page<Book> bookPage = bookRepo.searchBooks(null, "Jane Austen", null, null, Pageable.unpaged());
        assertThat(bookPage.getContent().size()).isEqualTo(1);
    }

    private void shouldReturnAllBooksContainingTitle() {
        Page<Book> bookPage = bookRepo.searchBooks("To Kill a Mockingbird", null, null, null, Pageable.unpaged());
        assertThat(bookPage.getContent().size()).isEqualTo(1);
    }

    private void shouldReturnAllBooksWithMatchingSearchPair() {
        Page<Book> bookPage = bookRepo.searchBooks(null, "J.R.R. Tolkien", null, 1940, Pageable.unpaged());
        assertThat(bookPage.getContent().size()).isEqualTo(1);
    }
}