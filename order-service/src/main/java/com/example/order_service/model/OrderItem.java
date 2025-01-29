package com.example.order_service.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long quantity;
    private BigDecimal price; // Price per unit
    private BigDecimal totalPrice; // Quantity * Price
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
