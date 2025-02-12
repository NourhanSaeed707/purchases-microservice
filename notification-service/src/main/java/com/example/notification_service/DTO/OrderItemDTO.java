package com.example.notification_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItemDTO {
    private Long id;
    private Long productId;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
