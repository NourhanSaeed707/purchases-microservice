package com.example.product_service.mapper;
import com.example.product_service.DTO.ProductDTO;
import com.example.product_service.model.Category;
import com.example.product_service.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .build();
    }

    public Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .category(Category.builder().id(productDTO.getCategoryId()).build())
                .build();
    }
}
