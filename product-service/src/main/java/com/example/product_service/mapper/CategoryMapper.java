package com.example.product_service.mapper;
import com.example.product_service.DTO.CategoryDTO;
import com.example.product_service.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryDTO toDto(Category category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .build();
    }
}
