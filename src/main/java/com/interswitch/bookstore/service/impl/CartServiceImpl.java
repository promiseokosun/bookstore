package com.interswitch.bookstore.service.impl;

import com.interswitch.bookstore.dto.request.AddToCartRequest;
import com.interswitch.bookstore.dto.response.BookResponse;
import com.interswitch.bookstore.exception.CustomException;
import com.interswitch.bookstore.model.Book;
import com.interswitch.bookstore.model.Cart;
import com.interswitch.bookstore.repo.CartRepo;
import com.interswitch.bookstore.service.BookService;
import com.interswitch.bookstore.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final BookService bookService;

    @Override
    public void addToCart(Long userId, AddToCartRequest request) {
        Book book = bookService.findBook(request.getBookId());
        Optional<Cart> optionalCart = cartRepo.findByUserId(userId);

        Cart cart;
        if(optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = Cart.builder()
                    .userId(userId)
                    .build();
        }

        cart.addBook(book);
        cartRepo.save(cart);
    }

    @Override
    public List<BookResponse> getAllBooksInCart(Long userId) {
        return findCart(userId)
                .getBooks()
                .stream()
                .map(bookService::buildBookResponse)
                .collect(Collectors.toList());
    }


    @Override
    public Cart findCart(Long userId) {
        return cartRepo.findByUserId(userId).orElseThrow(() ->
                new CustomException("Cart not found for user with id: " + userId, HttpStatus.NOT_FOUND));
    }

    @Override
    public void clearCart(Cart cart) {
        cart.clearCart();
        cartRepo.save(cart);
    }

}
