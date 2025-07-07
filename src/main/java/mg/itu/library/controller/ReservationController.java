package mg.itu.library.controller;

import mg.itu.library.model.Reservation;
import mg.itu.library.service.ReservationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService service;
    public ReservationController(ReservationService service) {
        this.service = service;
    }
    @GetMapping
    public List<Reservation> getAll() {
        return service.findAll();
    }
}
