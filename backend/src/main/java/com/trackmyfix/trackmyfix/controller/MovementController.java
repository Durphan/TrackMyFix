package com.trackmyfix.trackmyfix.controller;

import com.trackmyfix.trackmyfix.entity.Action;
import com.trackmyfix.trackmyfix.entity.Movement;
import com.trackmyfix.trackmyfix.services.Impl.MovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movement")
@AllArgsConstructor
@Tag(name = "Movement Management", description = "APIs for managing device movements")
public class MovementController {

    private final MovementService movementService;

    @GetMapping
    @Operation(summary = "Get all movements", description = "Retrieve a paginated list of all movements")
    public ResponseEntity<Map<String, Object>> findAllMovements() {
        Map<String, Object> response = movementService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movement by ID", description = "Retrieve a specific movement by its ID")
    public ResponseEntity<Movement> findById(@PathVariable Long id) {
        Movement movement = movementService.findById(id);
        return ResponseEntity.ok(movement);
    }

    @GetMapping("/action/{action}")
    @Operation(summary = "Get movements by action", description = "Retrieve movements filtered by action type")
    public ResponseEntity<List<Movement>> findByAction(@PathVariable Action action) {
        List<Movement> movements = movementService.findByAction(action);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/actions")
    @Operation(summary = "Get all action types", description = "Retrieve a list of all possible action types")
    public ResponseEntity<List<String>> getAllActions() {
        List<String> actions = movementService.getAllAction();
        return ResponseEntity.ok(actions);
    }
}
