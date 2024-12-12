package com.interswitch.bookstore.service;

import com.interswitch.bookstore.dto.request.OrderRequest;

public interface PaymentService {

    String processPayment(OrderRequest request);
}
