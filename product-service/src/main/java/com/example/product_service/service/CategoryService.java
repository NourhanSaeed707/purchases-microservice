package com.example.product_service.service;

import com.example.product_service.DTO.CategoryDTO;
import com.example.product_service.DTO.ProductDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();

    CategoryDTO create(CategoryDTO categoryDTO);

    CategoryDTO update(Long id, CategoryDTO categoryDTO);

    CategoryDTO getOne(Long id);

    boolean delete(Long id);
}
