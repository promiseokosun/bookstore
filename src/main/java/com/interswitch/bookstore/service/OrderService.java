package com.interswitch.bookstore.service;

import com.interswitch.bookstore.dto.request.OrderRequest;
import com.interswitch.bookstore.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponse checkout(Long userId, OrderRequest request);

    Page<OrderResponse> getOrderHistory(Long userId, Pageable pageable);
}
