package mg.itu.library.controller;

import mg.itu.library.model.StatutReservation;
import mg.itu.library.service.StatutReservationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/statut-reservations")
public class StatutReservationController {
    private final StatutReservationService service;
    public StatutReservationController(StatutReservationService service) {
        this.service = service;
    }
    @GetMapping
    public List<StatutReservation> getAll() {
        return service.findAll();
    }
}
