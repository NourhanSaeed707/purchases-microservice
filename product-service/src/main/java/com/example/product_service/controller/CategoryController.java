package com.example.product_service.controller;
import com.example.product_service.DTO.CategoryDTO;
import com.example.product_service.DTO.Role;
import com.example.product_service.DTO.Users;
import com.example.product_service.client.UserClient;
import com.example.product_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserClient userClient;

    @GetMapping("/public/")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDTO> getAll(@RequestHeader("Authorization") String token) {
//        ResponseEntity<Optional<Users>> userResponse = userClient.getUserInfo(token);
//        Users user = userResponse.getBody().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
//        if (user.getRole() != Role.ADMIN) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
//        }
        return categoryService.getAll();
    }

    @PostMapping("/admin/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO, @RequestHeader("X-User-Roles") String roles) {
        System.out.println("insiiiiiide create new categoooooory");
        if (!roles.contains("USER")) {
            throw new RuntimeException("Access denid");
        }
        return ResponseEntity.ok(categoryService.create(categoryDTO));
    }

    @PutMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO, @RequestHeader("Authorization") String token) {
        ResponseEntity<Optional<Users>> userResponse = userClient.getUserInfo(token);
        Users user = userResponse.getBody().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
        return categoryService.update(id, categoryDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getOne(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        ResponseEntity<Optional<Users>> userResponse = userClient.getUserInfo(token);
        Users user = userResponse.getBody().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
        return categoryService.getOne(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        ResponseEntity<Optional<Users>> userResponse = userClient.getUserInfo(token);
        Users user = userResponse.getBody().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
        boolean deleted = categoryService.delete(id);
        if(deleted) {
            return ResponseEntity.ok("Product deleted successfully");
        }
        return ResponseEntity.ok("Something went wrong");
    }
}
