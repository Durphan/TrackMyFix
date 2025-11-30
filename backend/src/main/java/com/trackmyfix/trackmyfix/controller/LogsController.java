package com.trackmyfix.trackmyfix.controller;

import com.trackmyfix.trackmyfix.Dto.Response.UserChangeResponseDTO;
import com.trackmyfix.trackmyfix.services.Impl.UserChangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/logs")
@AllArgsConstructor
@Tag(name = "Logs Management", description = "APIs for managing user change logs")
public class LogsController {

    private UserChangeService userChangeService;

    @GetMapping("/userchanges")
    @Operation(summary = "Get all user changes", description = "Retrieve a set of all user change logs")
    private ResponseEntity<Set<UserChangeResponseDTO>> findAll() {
        return ResponseEntity.ok(userChangeService.findAll());
    }
}
