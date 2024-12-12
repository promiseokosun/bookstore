package com.interswitch.bookstore.service.impl;

import com.interswitch.bookstore.dto.request.OrderRequest;
import com.interswitch.bookstore.enums.PaymentMethod;
import com.interswitch.bookstore.exception.CustomException;
import com.interswitch.bookstore.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Override
    public String processPayment(OrderRequest request) {
        switch (Enum.valueOf(PaymentMethod.class, request.getPaymentMethod())) {
            case PaymentMethod.WEB -> {
                log.info("Simulating payment with WEB");
                return request.getPaymentMethod();
            }
            case PaymentMethod.USSD -> {
                log.info("Simulating payment with USSD");
                return request.getPaymentMethod();
            }
            case PaymentMethod.TRANSFER -> {
                log.info("Simulating payment with TRANSFER");
                return request.getPaymentMethod();
            }
            default -> {
                log.info("Invalid payment method");
                throw new CustomException("Invalid payment method", HttpStatus.BAD_REQUEST);
            }
        }
    }

}
