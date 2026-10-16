package mx.edu.utez.casino.controller.dto.tiro;

import mx.edu.utez.casino.model.partida.EstadoPartida;
import mx.edu.utez.casino.model.tiro.Tiro;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TiroResponseDTO {
    private Long id;
    private Long partidaId;
    private int valorDado1;
    private int valorDado2;
    private int suma;
    private boolean esGanador;
    private String resultado;            // GANA, PIERDE o CONTINUA
    private EstadoPartida estadoPartida;
    private BigDecimal saldoJugador;

    public static TiroResponseDTO from(Tiro t, String resultado) {
        return new TiroResponseDTO(t.getId(), t.getPartida().getId(), t.getValorDado1(),
                t.getValorDado2(), t.getSuma(), t.isEsGanador(), resultado,
                t.getPartida().getEstado(), t.getPartida().getJugador().getSaldo());
    }
}
