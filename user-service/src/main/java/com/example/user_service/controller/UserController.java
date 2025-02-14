package com.example.user_service.controller;
import com.example.user_service.dto.UserDTO;
import com.example.user_service.model.Users;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Users update(@PathVariable("id") Long id, @RequestBody Users updatedUser){
       return userService.update(id, updatedUser);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Users getOne(@PathVariable("id") Long id){
        return userService.getOne(id);
    }

    @GetMapping("/get-user-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public UserDTO getUserById(@PathVariable("id") Long id){
        System.out.println("insiiiide get user by id: " + id);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        boolean deleted = userService.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Deleted Successfully");
        }
        return ResponseEntity.ok("Something went wrong");
    }

    @GetMapping("/get-by-email/{email}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public Users getUserByEmail(@PathVariable("email") String email) {
        return userService.getUserByEmail(email);
    }
}
