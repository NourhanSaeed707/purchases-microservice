package com.example.product_service.service;
import com.example.product_service.DTO.ProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();

    ProductDTO create(ProductDTO productDTO);

    ProductDTO update(Long id, ProductDTO productDTO);

    ProductDTO getOne(Long id);

    boolean delete(Long id);
}
