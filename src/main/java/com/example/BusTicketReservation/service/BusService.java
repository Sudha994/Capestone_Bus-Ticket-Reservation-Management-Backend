package com.example.BusTicketReservation.service;

import com.example.BusTicketReservation.entity.Bus;
import com.example.BusTicketReservation.repository.BusRepository;
import com.example.BusTicketReservation.repository.TripRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    private final BusRepository busRepo;
    private final TripRepository tripRepo;

    public BusService(BusRepository busRepo, TripRepository tripRepo) {
        this.busRepo = busRepo;
        this.tripRepo = tripRepo;
    }

    public Bus create(Bus req) {
        // sensible defaults/guards
        if (req.getTotalSeats() <= 0)
            req.setTotalSeats(40);
        int avail = req.getAvailableSeats() > 0 ? req.getAvailableSeats() : req.getTotalSeats();
        if (avail > req.getTotalSeats())
            avail = req.getTotalSeats();
        req.setAvailableSeats(avail);
        return busRepo.save(req);
    }

    public List<Bus> findAll() {
        return busRepo.findAll();
    }

    public Optional<Bus> findById(Long id) {
        return busRepo.findById(id);
    }

    /** Buses that are scheduled on a given route (via trips). */
    public List<Bus> findByRouteId(Long routeId) {
        return tripRepo.findBusesByRouteId(routeId);
    }

    public Bus update(Long id, Bus req) {
        Bus b = busRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus not found"));

        if (req.getBusNumber() != null)
            b.setBusNumber(req.getBusNumber());
        if (req.getBusType() != null)
            b.setBusType(req.getBusType());
        if (req.getTotalSeats() > 0)
            b.setTotalSeats(req.getTotalSeats());

        // allow setting availableSeats to 0 explicitly
        if (req.getAvailableSeats() >= 0) {
            int avail = req.getAvailableSeats();
            if (avail > b.getTotalSeats())
                avail = b.getTotalSeats();
            b.setAvailableSeats(avail);
        }
        return busRepo.save(b);
    }

    public void delete(Long id) {
        if (!busRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus not found");
        busRepo.deleteById(id);
    }
}
