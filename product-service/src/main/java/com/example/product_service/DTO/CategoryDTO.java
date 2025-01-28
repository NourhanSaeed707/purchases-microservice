package com.example.product_service.DTO;
import com.example.product_service.model.Product;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;
    @NotNull(message = "Category name is required")
    private String name;
    @NotNull(message = "Category description is required")
    private String description;
//    private List<Product> products;
}
