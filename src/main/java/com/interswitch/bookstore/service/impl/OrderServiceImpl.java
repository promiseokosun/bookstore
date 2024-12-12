package com.interswitch.bookstore.service.impl;

import com.interswitch.bookstore.dto.request.OrderRequest;
import com.interswitch.bookstore.dto.response.OrderResponse;
import com.interswitch.bookstore.enums.PaymentMethod;
import com.interswitch.bookstore.model.Cart;
import com.interswitch.bookstore.model.Order;
import com.interswitch.bookstore.repo.OrderRepo;
import com.interswitch.bookstore.service.BookService;
import com.interswitch.bookstore.service.CartService;
import com.interswitch.bookstore.service.OrderService;
import com.interswitch.bookstore.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final CartService cartService;
    private final BookService bookService;
    private final PaymentService paymentService;


    @Override
    public OrderResponse checkout(Long userId, OrderRequest request) {

        Cart cart = cartService.findCart(userId);

        paymentService.processPayment(request);

        Order order = new Order();
        order.setBooks(cart.getBooks());
        order.setUserId(userId);
        order.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()));
        order.setPaymentReference(request.getPaymentReference());
        orderRepo.save(order);

        cartService.clearCart(cart);

        return buildOrderResponse(order);
    }


    @Override
    public Page<OrderResponse> getOrderHistory(Long userId, Pageable pageable) {

        Page<Order> orderPage = orderRepo.findAllByUserId(userId, pageable);

        List<OrderResponse> orderResponseList =
                orderPage.getContent().stream().map(this::buildOrderResponse).collect(Collectors.toList());

        if (orderPage.getContent().isEmpty()) {
            return new PageImpl<>(new ArrayList<>(), Pageable.unpaged(), orderPage.getTotalElements());
        }

        return new PageImpl<>(orderResponseList, Pageable.unpaged(), orderPage.getTotalElements());
    }


    private OrderResponse buildOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .paymentMethod(order.getPaymentMethod())
                .paymentReference(order.getPaymentReference())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .books(order.getBooks()
                        .stream()
                        .map(bookService::buildBookResponse)
                        .collect(Collectors.toList()))
                .build();
    }


}
