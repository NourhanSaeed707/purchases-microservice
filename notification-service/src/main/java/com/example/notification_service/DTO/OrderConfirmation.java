package com.example.notification_service.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderConfirmation {
    private Long orderId;
    private BigDecimal totalAmount;
    private Long userId;
    List<OrderItemDTO> orderItemDTOS;
}
