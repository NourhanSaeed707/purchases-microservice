package com.example.product_service.service.Impl;
import com.example.product_service.DTO.CategoryDTO;
import com.example.product_service.mapper.CategoryMapper;
import com.example.product_service.model.Category;
import com.example.product_service.repository.CategoryRepository;
import com.example.product_service.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(mapper::toDto).toList();
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = mapper.toEntity(categoryDTO);
        Category created = categoryRepository.save(category);
        return mapper.toDto(category);
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        Category categoryExist = categoryRepository.findById(id).orElseThrow(() -> EntityNotFoundException("category not found with id " + id));
        if(categoryExist != null) {
            categoryExist.setName(categoryDTO.getName());
            categoryExist.setDescription(categoryDTO.getDescription());
            categoryRepository.save(categoryExist);
        }
        return categoryDTO;
    }

    @Override
    public CategoryDTO getOne(Long id) {
        Category categoryExist = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("category not found with id " + id));
        return mapper.toDto(categoryExist);
    }

    @Override
    public boolean delete(Long id) {
        Category categoryExist = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("category not found with id " + id));
        if(categoryExist != null) {
            categoryRepository.delete(categoryExist);
            return true;
        }
        return false;
    }
}
