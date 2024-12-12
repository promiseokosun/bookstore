package com.interswitch.bookstore.dto.request;

import com.interswitch.bookstore.enums.PaymentMethod;
import com.interswitch.bookstore.validator.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotBlank(message = "paymentReference is required")
    private String paymentReference;
    @NotBlank(message = "paymentMethod must be either WEB|USSD|TRANSFER")
    @ValueOfEnum(enumClass = PaymentMethod.class)
    private String paymentMethod;
}
