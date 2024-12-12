package com.interswitch.bookstore.service.impl;

import com.interswitch.bookstore.BookStoreApplicationTests;
import com.interswitch.bookstore.dto.request.OrderRequest;
import com.interswitch.bookstore.enums.PaymentMethod;
import com.interswitch.bookstore.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceImplTest extends BookStoreApplicationTests {

    @Autowired
    private PaymentService paymentService;

    @Test
    void processPayment() {
        String paymentMethod = PaymentMethod.WEB.name();
        OrderRequest request = OrderRequest.builder()
                .paymentReference(RandomStringUtils.randomAlphanumeric(15))
                .paymentMethod(paymentMethod)
                .build();

        assertThat(paymentService.processPayment(request)).isEqualTo(paymentMethod);
    }
}