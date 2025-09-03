package com.example.BusTicketReservation.controller;

import com.example.BusTicketReservation.entity.Route;
import com.example.BusTicketReservation.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/routes")
public class RouteController {

    private final RouteService service;

    public RouteController(RouteService service) {
        this.service = service;
    }

    @Operation(summary = "Creates a new route (Admin).")
    @PostMapping
    public ResponseEntity<Route> create(@RequestBody Route req) {
        return ResponseEntity.ok(service.create(req));
    }

    @Operation(summary = "Get routes. If id is provided, returns one; otherwise returns all.")
    @GetMapping
    public ResponseEntity<?> getRoutes(
            @Parameter(description = "Route id") @RequestParam(value = "id", required = false) Long id) {
        return (id == null) ? ResponseEntity.ok(service.findAll())
                : service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletes a route (Admin).")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Route> update(@PathVariable Long id, @RequestBody Route req) {
        return ResponseEntity.ok(service.update(id, req));
    }
}
