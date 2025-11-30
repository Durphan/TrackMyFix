package com.trackmyfix.trackmyfix.controller;

import com.trackmyfix.trackmyfix.entity.State;
import com.trackmyfix.trackmyfix.entity.Type;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.trackmyfix.trackmyfix.entity.Device;
import com.trackmyfix.trackmyfix.services.Impl.DeviceService;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/device")
@AllArgsConstructor
@Tag(name = "Device Management", description = "APIs for managing devices")
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping
    @Operation(summary = "Get all devices", description = "Retrieve a paginated list of all devices")
    public ResponseEntity<Map<String, Object>> findAllDevices() {
        Map<String, Object> response = deviceService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get device by ID", description = "Retrieve a specific device by its ID")
    public ResponseEntity<Device> findById(@PathVariable Long id) {
        Device device = deviceService.findById(id);
        return ResponseEntity.ok(device);
    }

    @GetMapping("/serial-number/{serialNumber}")
    @Operation(summary = "Get device by serial number", description = "Retrieve a device by its serial number")
    public ResponseEntity<Device> findBySerialNumber(@PathVariable String serialNumber) {
        Device device = deviceService.findBySerialNumber(serialNumber);
        return ResponseEntity.ok(device);
    }

    @GetMapping("/states")
    @Operation(summary = "Get all device states", description = "Retrieve a list of all possible device states")
    public ResponseEntity<List<String>> getAllStates() {
        List<String> states = deviceService.getAllStates();
        return ResponseEntity.ok(states);
    }

    @GetMapping("/types")
    @Operation(summary = "Get all device types", description = "Retrieve a list of all possible device types")
    public ResponseEntity<List<String>> getAllTypes() {
        List<String> types = deviceService.getAllTypes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/state/{state}")
    @Operation(summary = "Get devices by state", description = "Retrieve devices filtered by state")
    public ResponseEntity<List<Device>> findByState(@PathVariable State state) {
        List<Device> devices = deviceService.findByState(state);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get devices by type", description = "Retrieve devices filtered by type")
    public ResponseEntity<List<Device>> findByType(@PathVariable Type type) {
        List<Device> devices = deviceService.findByType(type);
        return ResponseEntity.ok(devices);
    }
}
