package com.example.BusTicketReservation.controller;

import com.example.BusTicketReservation.entity.Bus;
import com.example.BusTicketReservation.service.BusService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buses")
public class BusController {

    private final BusService service;

    public BusController(BusService service) {
        this.service = service;
    }

    @Operation(summary = "List all buses.")
    @GetMapping
    public ResponseEntity<List<Bus>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Get a bus by id.")
    @GetMapping("/{id}")
    public ResponseEntity<Bus> getOne(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get buses that have trips on a given route id.")
    @GetMapping("/by-route/{routeId}")
    public ResponseEntity<List<Bus>> getByRoute(@PathVariable Long routeId) {
        return ResponseEntity.ok(service.findByRouteId(routeId));
    }

    @Operation(summary = "Creates a new bus (Admin).")
    @PostMapping
    public ResponseEntity<Bus> create(@RequestBody Bus req) {
        return ResponseEntity.ok(service.create(req));
    }

    @Operation(summary = "Updates an existing bus (Admin).")
    @PutMapping("/{id}")
    public ResponseEntity<Bus> update(@PathVariable Long id, @RequestBody Bus req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @Operation(summary = "Deletes a bus (Admin).")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
