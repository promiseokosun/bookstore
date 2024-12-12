package com.interswitch.bookstore.service.impl;

import com.interswitch.bookstore.BookStoreApplicationTests;
import com.interswitch.bookstore.dto.request.OrderRequest;
import com.interswitch.bookstore.dto.response.OrderResponse;
import com.interswitch.bookstore.enums.PaymentMethod;
import com.interswitch.bookstore.model.Cart;
import com.interswitch.bookstore.model.Order;
import com.interswitch.bookstore.repo.CartRepo;
import com.interswitch.bookstore.repo.OrderRepo;
import com.interswitch.bookstore.service.BookService;
import com.interswitch.bookstore.service.CartService;
import com.interswitch.bookstore.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Slf4j
class OrderServiceImplTest extends BookStoreApplicationTests {

    @Autowired
    @InjectMocks
    private OrderServiceImpl orderService;

    @MockBean
    private PaymentServiceImpl paymentService;

    @MockBean
    private CartServiceImpl cartService;

    @MockBean
    private BookServiceImpl bookService;

    @MockBean
    private OrderRepo orderRepo;

    private Long userId = 1l;
    private Long bookId = 1l;

    Cart cartMock = new Cart();
    Order orderMock = new Order();


    @Test
    void checkout() {
        cartMock.setUserId(userId);
        cartMock.setBooks(new ArrayList<>());

        String paymentReference = RandomStringUtils.randomAlphanumeric(15);
        orderMock = new Order();
        orderMock.setBooks(cartMock.getBooks());
        orderMock.setUserId(userId);
        orderMock.setPaymentMethod(PaymentMethod.TRANSFER);
        orderMock.setPaymentReference(paymentReference);

        when(cartService.findCart(userId)).thenReturn(cartMock);
        when(orderRepo.save(orderMock)).thenReturn(orderMock);
        doNothing().when(cartService).clearCart(cartMock);
        when(paymentService.processPayment(any(OrderRequest.class))).thenReturn("Payment successful with WEB channel");

        OrderRequest request = OrderRequest.builder()
                .paymentMethod(PaymentMethod.TRANSFER.name())
                .paymentReference(paymentReference)
                .build();

        OrderResponse response = orderService.checkout(userId, request);
        log.info("Checkout response: {}", response);

        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getPaymentReference()).isEqualTo(paymentReference);
    }

    @Test
    void getOrderHistory() {
        when(orderRepo.findAllByUserId(userId, Pageable.unpaged()))
                .thenReturn(new PageImpl<>(Collections.singletonList(orderMock)));

        assertThat(orderService.getOrderHistory(userId, Pageable.unpaged()).getContent()).isNotEmpty();
    }
}