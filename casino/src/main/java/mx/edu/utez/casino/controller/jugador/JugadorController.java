package mx.edu.utez.casino.controller.jugador;

import mx.edu.utez.casino.controller.dto.jugador.JugadorRequestDTO;
import mx.edu.utez.casino.controller.dto.jugador.JugadorResponseDTO;
import mx.edu.utez.casino.controller.dto.jugador.RecargaRequestDTO;
import mx.edu.utez.casino.service.jugador.JugadorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jugador")
@CrossOrigin({"*"})
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @PostMapping
    public ResponseEntity<JugadorResponseDTO> crear(@Valid @RequestBody JugadorRequestDTO dto) {
        return ResponseEntity.status(201).body(jugadorService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<JugadorResponseDTO>> listar() {
        return ResponseEntity.ok(jugadorService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.obtenerPorId(id));
    }

    @PatchMapping("/{id}/recargar")
    public ResponseEntity<JugadorResponseDTO> recargarSaldo(@PathVariable Long id,
                                                            @Valid @RequestBody RecargaRequestDTO dto) {
        return ResponseEntity.ok(jugadorService.recargarSaldo(id, dto.getMonto()));
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<JugadorResponseDTO> activar(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.cambiarEstado(id, true));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<JugadorResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.cambiarEstado(id, false));
    }
}
