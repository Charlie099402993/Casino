package mx.edu.utez.casino.controller.partida;

import mx.edu.utez.casino.controller.dto.partida.IniciarPartidaRequestDTO;
import mx.edu.utez.casino.controller.dto.partida.PartidaResponseDTO;
import mx.edu.utez.casino.service.partida.PartidaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partida")
@CrossOrigin({"*"})
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<PartidaResponseDTO> iniciarPartida(@Valid @RequestBody IniciarPartidaRequestDTO dto) {
        return ResponseEntity.status(201).body(partidaService.iniciarPartida(dto));
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<PartidaResponseDTO> finalizarPartidaManual(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.finalizarPartidaManual(id));
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<PartidaResponseDTO>> obtenerHistorialPartida(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(partidaService.obtenerHistorialPartida(jugadorId));
    }
}
