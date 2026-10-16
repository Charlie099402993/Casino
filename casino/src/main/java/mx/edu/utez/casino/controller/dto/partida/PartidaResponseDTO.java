package mx.edu.utez.casino.controller.dto.partida;

import mx.edu.utez.casino.model.partida.EstadoPartida;
import mx.edu.utez.casino.model.partida.Partida;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PartidaResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private EstadoPartida estado;
    private Long jugadorId;
    private String jugadorNombre;
    private BigDecimal apuesta;

    public static PartidaResponseDTO from(Partida p) {
        return new PartidaResponseDTO(p.getId(), p.getFecha(), p.getEstado(),
                p.getJugador().getId(), p.getJugador().getNombre(), p.getApuesta());
    }
}
