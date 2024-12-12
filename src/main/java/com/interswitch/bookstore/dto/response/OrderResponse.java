package com.interswitch.bookstore.dto.response;

import com.interswitch.bookstore.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long userId;
    private PaymentMethod paymentMethod;
    private String paymentReference;
    private List<BookResponse> books;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
