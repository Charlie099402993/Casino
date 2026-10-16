package mx.edu.utez.casino.controller.dto.jugador;

import mx.edu.utez.casino.model.jugador.Jugador;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class JugadorResponseDTO {
    private Long id;
    private String nombre;
    private BigDecimal saldo;
    private boolean activo;

    public static JugadorResponseDTO from(Jugador j) {
        return new JugadorResponseDTO(j.getId(), j.getNombre(), j.getSaldo(), j.isActivo());
    }
}
