package mx.edu.utez.casino.controller.tiro;

import mx.edu.utez.casino.controller.dto.tiro.TiroResponseDTO;
import mx.edu.utez.casino.service.tiro.TiroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tiro")
@CrossOrigin({"*"})
public class TiroController {

    private final TiroService tiroService;

    public TiroController(TiroService tiroService) {
        this.tiroService = tiroService;
    }

    @PostMapping("/{partidaId}")
    public ResponseEntity<TiroResponseDTO> realizarTiro(@PathVariable Long partidaId) {
        return ResponseEntity.status(201).body(tiroService.realizarTiro(partidaId));
    }
}
