package com.interswitch.bookstore.service.impl;

import com.interswitch.bookstore.BookStoreApplicationTests;
import com.interswitch.bookstore.dto.request.AddToCartRequest;
import com.interswitch.bookstore.enums.Genre;
import com.interswitch.bookstore.model.Book;
import com.interswitch.bookstore.model.Cart;
import com.interswitch.bookstore.repo.CartRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
class CartServiceImplTest extends BookStoreApplicationTests {

    @Autowired
    @InjectMocks
    private CartServiceImpl cartService;

    @MockBean
    private BookServiceImpl bookService;

    @MockBean
    private CartRepo cartRepo;

    private Long userId = 1l;
    private Long bookId = 1l;

    Cart cartMock = new Cart();


    @Test
    void addToCart() {
        cartMock.setUserId(userId);
        cartMock.setBooks(new ArrayList<>());
        when(cartRepo.save(any(Cart.class))).thenReturn(cartMock);
        when(cartRepo.findByUserId(userId)).thenReturn(Optional.of(cartMock));

        Book bookMock = new Book();
        bookMock.setAuthor("Test Author");
        bookMock.setGenre(Genre.FICTION);
        bookMock.setIsbn("1002001-00");
        bookMock.setTitle("Test Title");
        bookMock.setAvailable(true);
        when(bookService.findBook(bookId)).thenReturn(bookMock);

        AddToCartRequest request = AddToCartRequest.builder()
                .bookId(bookId)
                .build();

        cartService.addToCart(userId, request);

        assertThat(cartService.getAllBooksInCart(userId)).isNotEmpty();
    }

    @Test
    void getAllBooksInCart() {
        addToCart();
        assertThat(cartService.getAllBooksInCart(userId)).isNotEmpty();
    }

    @Test
    void findCart() {
        addToCart();
        assertThat(cartService.findCart(userId)).isNotNull();
    }

    @Test
    void clearCart() {
        addToCart();
        Cart cart = cartService.findCart(userId);
        log.info("Before clearing the cart: {}", cart.getBooks());
        cartService.clearCart(cartMock);
        log.info("After clearing the cart: {}", cart.getBooks());
        assertThat(cart.getBooks()).isEmpty();
    }
}