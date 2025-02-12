package com.example.product_service.controller;
import com.example.product_service.DTO.CategoryDTO;
import com.example.product_service.client.UserClient;
import com.example.product_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserClient userClient;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDTO> getAll(@RequestHeader("Authorization") String token) {
        return categoryService.getAll();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO, @RequestHeader("X-User-Roles") String roles) {
        if (!roles.contains("USER")) {
            throw new RuntimeException("Access denid");
        }
        return ResponseEntity.ok(categoryService.create(categoryDTO));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoryDTO update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO, @RequestHeader("Authorization") String token) {
        return categoryService.update(id, categoryDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getOne(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        return categoryService.getOne(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        boolean deleted = categoryService.delete(id);
        if(deleted) {
            return ResponseEntity.ok("Product deleted successfully");
        }
        return ResponseEntity.ok("Something went wrong");
    }
}
