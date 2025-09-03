package com.example.BusTicketReservation.controller;

import com.example.BusTicketReservation.dto.BookingRequest;
import com.example.BusTicketReservation.dto.BookingResponse;
import com.example.BusTicketReservation.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public BookingResponse book(@RequestBody BookingRequest request) {
        return bookingService.bookTicket(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long id) {
        return bookingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/my")
    public List<BookingResponse> myBookings(Authentication authentication) {
        return bookingService.getMyBookings(authentication.getName());
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }
}
