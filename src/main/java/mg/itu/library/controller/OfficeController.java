package mg.itu.library.controller;

import mg.itu.library.service.LivreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OfficeController {
    private final LivreService livreService;

    public OfficeController(LivreService livreService) {
        this.livreService = livreService;
    }

    @GetMapping("/backoffice")
    public String backOffice() {
        return "backoffice/index";
    }
}
