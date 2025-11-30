package com.trackmyfix.trackmyfix.controller;

import com.trackmyfix.trackmyfix.Dto.Request.OrderRequest;
import com.trackmyfix.trackmyfix.Dto.Request.OrderUpdateRequest;
import com.trackmyfix.trackmyfix.entity.Order;
import com.trackmyfix.trackmyfix.services.Impl.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/work-order")
@AllArgsConstructor
@Tag(name = "Work Order Management", description = "APIs for managing work orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Get all work orders", description = "Retrieve a paginated list of all work orders")
    public ResponseEntity<Map<String, Object>> findAllOrders() {
        Map<String, Object> response = orderService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/number/{number}")
    @Operation(summary = "Get work order by number", description = "Retrieve a work order by its number")
    public ResponseEntity<Order> findOrderByNumber(@PathVariable String number) {
        Order order = orderService.findByNumber(number);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get work order by ID", description = "Retrieve a specific work order by its ID")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    @Operation(summary = "Create a new work order", description = "Create a new work order with the provided details")
    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a work order", description = "Update an existing work order by its ID")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id,
            @RequestBody @Valid OrderUpdateRequest orderUpdateRequest) {
        Order updatedOrder = orderService.updateOrder(id, orderUpdateRequest);
        return ResponseEntity.ok(updatedOrder);
    }

    @PatchMapping("/{id}/set-active")
    @Operation(summary = "Set work order active status", description = "Set the active status of a work order")
    public ResponseEntity<Void> setActiveOrder(@PathVariable Long id, @RequestBody Map<String, Boolean> requestBody) {
        boolean active = requestBody.get("active");
        orderService.setActiveOrder(id, active);
        return ResponseEntity.noContent().build();
    }
}
