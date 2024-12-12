package com.interswitch.bookstore.controller;

import com.interswitch.bookstore.dto.request.OrderRequest;
import com.interswitch.bookstore.dto.response.apiresponse.ApiResponse;
import com.interswitch.bookstore.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order/users/{userId}")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@PathVariable Long userId,
                                      @Valid @RequestBody OrderRequest request) {

        return new ResponseEntity<>(ApiResponse.successResponse(orderService.checkout(userId, request)), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getAllBooksInCart(@PathVariable Long userId, Pageable pageable) {
        return new ResponseEntity<>(ApiResponse.successResponse(orderService.getOrderHistory(userId, pageable)), HttpStatus.OK);
    }
}
