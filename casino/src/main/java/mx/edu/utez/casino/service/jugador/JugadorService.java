package mx.edu.utez.casino.service.jugador;

import mx.edu.utez.casino.controller.dto.jugador.JugadorRequestDTO;
import mx.edu.utez.casino.controller.dto.jugador.JugadorResponseDTO;
import mx.edu.utez.casino.exception.customExceptions.NotFoundException;
import mx.edu.utez.casino.model.jugador.Jugador;
import mx.edu.utez.casino.repository.jugador.JugadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    private Jugador buscarPorId(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El jugador con id " + id + " no existe"));
    }

    @Transactional
    public JugadorResponseDTO crear(JugadorRequestDTO dto) {
        Jugador jugador = new Jugador();
        jugador.setNombre(dto.getNombre());
        jugador.setSaldo(dto.getSaldo());
        jugador.setActivo(true);
        return JugadorResponseDTO.from(jugadorRepository.save(jugador));
    }

    @Transactional(readOnly = true)
    public List<JugadorResponseDTO> listar() {
        return jugadorRepository.findAll().stream().map(JugadorResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public JugadorResponseDTO obtenerPorId(Long id) {
        return JugadorResponseDTO.from(buscarPorId(id));
    }

    // recargarSaldo: suma el monto al saldo del jugador
    @Transactional
    public JugadorResponseDTO recargarSaldo(Long id, BigDecimal monto) {
        Jugador jugador = buscarPorId(id);
        jugador.setSaldo(jugador.getSaldo().add(monto));
        return JugadorResponseDTO.from(jugadorRepository.save(jugador));
    }

    // Util para probar la validacion "jugador activo"
    @Transactional
    public JugadorResponseDTO cambiarEstado(Long id, boolean activo) {
        Jugador jugador = buscarPorId(id);
        jugador.setActivo(activo);
        return JugadorResponseDTO.from(jugadorRepository.save(jugador));
    }
}
