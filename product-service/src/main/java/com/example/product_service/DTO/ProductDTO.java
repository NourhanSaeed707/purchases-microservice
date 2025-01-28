package com.example.product_service.DTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    @NotNull(message = "Product name is required")
    private String name;
    @NotNull(message = "Description is required")
    private String description;
    @Positive(message = "Price should be positive")
    private BigDecimal price;
    @Positive(message = "Quantity should be positive")
    private Long quantity;
    @NotNull(message = "Product category id is required")
    private Long categoryId;
}
