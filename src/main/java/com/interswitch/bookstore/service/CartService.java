package com.interswitch.bookstore.service;

import com.interswitch.bookstore.dto.request.AddToCartRequest;
import com.interswitch.bookstore.dto.response.BookResponse;
import com.interswitch.bookstore.model.Cart;

import java.util.List;

public interface CartService {

    void addToCart(Long userId, AddToCartRequest request);

    List<BookResponse> getAllBooksInCart(Long userId);

    Cart findCart(Long userId);

    void clearCart(Cart cart);
}
