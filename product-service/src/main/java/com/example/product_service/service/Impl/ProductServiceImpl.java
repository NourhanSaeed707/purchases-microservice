package com.example.product_service.service.Impl;
import com.example.product_service.DTO.ProductDTO;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.model.Category;
import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;


    @Override
    public List<ProductDTO> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(mapper::toDTO).toList();
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        Product product = mapper.toEntity(productDTO);
        Product created = productRepository.save(product);
        return mapper.toDTO(created);
    }

    @Override
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product productExist = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        if(productExist != null) {
            productExist.setName(productDTO.getName());
            productExist.setDescription(productDTO.getDescription());
            productExist.setPrice(productDTO.getPrice());
            productExist.setQuantity(productDTO.getQuantity());
            productExist.setCategory(Category.builder().id(productDTO.getCategoryId()).build());
            productRepository.save(productExist);
        }
        return productDTO;
    }

    @Override
    public ProductDTO getOne(Long id) {
        Product productExist = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        return mapper.toDTO(productExist);
    }

    @Override
    public boolean delete(Long id) {
        Product productExist = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
        if(productExist != null) {
            productRepository.delete(productExist);
            return true;
        }
        return false;
    }
}
