package com.interswitch.bookstore.controller;

import com.interswitch.bookstore.dto.request.AddToCartRequest;
import com.interswitch.bookstore.dto.response.apiresponse.ApiResponse;
import com.interswitch.bookstore.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart/users/{userId}")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@PathVariable Long userId,
                                      @Valid @RequestBody AddToCartRequest request) {

        cartService.addToCart(userId, request);
        return new ResponseEntity<>(ApiResponse.successResponse(null), HttpStatus.OK);
    }

    @GetMapping("/view")
    public ResponseEntity<?> getAllBooksInCart(@PathVariable Long userId) {
        return new ResponseEntity<>(ApiResponse.successResponse(cartService.getAllBooksInCart(userId)), HttpStatus.OK);
    }
}
