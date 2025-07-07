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

    // Route alternative obfusquée pour récupérer toutes les réservations
    @GetMapping("/toutes-obfusquees")
    public List<Reservation> getAllObfusquees() {
        return service.recupererToutesReservations();
    }

    // Route alternative obfusquée pour créer une réservation
    @PostMapping("/creer-obfusquee")
    public String creerReservationObfusquee(@RequestBody Reservation reservation) {
        return service.creerReservationObfusquee(reservation);
    }
}
