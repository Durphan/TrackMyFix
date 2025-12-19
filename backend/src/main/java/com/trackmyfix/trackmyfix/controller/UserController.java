package com.trackmyfix.trackmyfix.controller;

import com.trackmyfix.trackmyfix.Dto.Request.UserRequestDTO;
import com.trackmyfix.trackmyfix.Dto.Response.UserResponseDTO;
import com.trackmyfix.trackmyfix.entity.Role;
import com.trackmyfix.trackmyfix.entity.UserJwtData;
import com.trackmyfix.trackmyfix.services.Impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/login")
    @Operation(summary = "User login", description = "Authenticate user with username/password or refresh token")
    public ResponseEntity<Map<String, String>> login(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password) {
        ResponseEntity<Map<String, String>> response = null;
        response = ResponseEntity.ok(userService.verify(username, password));
        return response;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(
            @RequestParam(name = "refresh_token") String token) {
        return ResponseEntity.ok(userService.refreshToken(token));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account")
    public ResponseEntity<UserResponseDTO> save(@Validated @RequestBody UserRequestDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/update")
    @Operation(summary = "Update user", description = "Update the current user's information")
    public ResponseEntity<UserResponseDTO> update(@Validated @RequestBody UserRequestDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.update(user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user by their ID")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping("/profile")
    @Operation(summary = "Get user profile", description = "Retrieve the current user's profile")
    public ResponseEntity<UserResponseDTO> profile() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        UserJwtData user = (UserJwtData) authentication.getPrincipal();
        return ResponseEntity.ok(userService.findById(user.getId()));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all users", description = "Retrieve a list of users filtered by role")
    public ResponseEntity<List<UserResponseDTO>> all(@RequestParam(name = "role") Optional<Role> role) {
        return ResponseEntity.ok(userService.findAll(role.orElse(Role.CLIENT)));
    }
}
