package com.example.product_service.controller;
import com.example.product_service.DTO.ProductDTO;
import com.example.product_service.DTO.Role;
import com.example.product_service.DTO.Users;
import com.example.product_service.client.UserClient;
import com.example.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserClient userClient;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<ProductDTO> getAll(@RequestHeader("Authorization") String token) {
//        ResponseEntity<Optional<Users>> userResponse = userClient.getUserInfo(token);
//        Users user = userResponse.getBody().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
//        if (user.getRole() != Role.ADMIN) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
//        }
        return productService.getAll();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ProductDTO create(@RequestBody ProductDTO productDTO, @RequestHeader("Authorization") String token) {
//        ResponseEntity<Optional<Users>> userResponse = userClient.getUserInfo(token);
//        Users user = userResponse.getBody().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
//        if (user.getRole() != Role.ADMIN) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
//        }
        return productService.create(productDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO productDTO, @RequestHeader("Authorization") String token) {
        ResponseEntity<Optional<Users>> userResponse = userClient.getUserInfo(token);
        Users user = userResponse.getBody().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
        return productService.update(id, productDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getOne(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        ResponseEntity<Optional<Users>> userResponse = userClient.getUserInfo(token);
        Users user = userResponse.getBody().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
        return productService.getOne(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        ResponseEntity<Optional<Users>> userResponse = userClient.getUserInfo(token);
        Users user = userResponse.getBody().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        if (user.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
        boolean deleted = productService.delete(id);
        if(deleted) {
            return ResponseEntity.ok("Product deleted successfully");
        }
        return ResponseEntity.ok("Something went wrong");
    }
}
