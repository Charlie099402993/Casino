package mx.edu.utez.casino.controller.dto.partida;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class IniciarPartidaRequestDTO {

    @NotNull(message = "El id del jugador es obligatorio")
    @Positive(message = "El id del jugador debe ser positivo")
    private Long jugadorId;

    @NotNull(message = "La apuesta es obligatoria")
    @DecimalMin(value = "0.01", message = "La apuesta debe ser mayor a 0")
    private BigDecimal apuesta;
}
