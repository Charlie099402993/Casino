package mx.edu.utez.casino.service.tiro;

import mx.edu.utez.casino.controller.dto.tiro.TiroResponseDTO;
import mx.edu.utez.casino.exception.customExceptions.BadRequestException;
import mx.edu.utez.casino.exception.customExceptions.NotFoundException;
import mx.edu.utez.casino.model.jugador.Jugador;
import mx.edu.utez.casino.model.partida.EstadoPartida;
import mx.edu.utez.casino.model.partida.Partida;
import mx.edu.utez.casino.model.tiro.Tiro;
import mx.edu.utez.casino.repository.jugador.JugadorRepository;
import mx.edu.utez.casino.repository.partida.PartidaRepository;
import mx.edu.utez.casino.repository.tiro.TiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class TiroService {

    private final TiroRepository tiroRepository;
    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final Random random = new Random();

    public TiroService(TiroRepository tiroRepository,
                       PartidaRepository partidaRepository,
                       JugadorRepository jugadorRepository) {
        this.tiroRepository = tiroRepository;
        this.partidaRepository = partidaRepository;
        this.jugadorRepository = jugadorRepository;
    }

    // realizarTiro
    @Transactional
    public TiroResponseDTO realizarTiro(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new NotFoundException("La partida con id " + partidaId + " no existe"));

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new BadRequestException("La partida no está en juego (estado: " + partida.getEstado() + ")");
        }

        // 1. Generar 2 valores aleatorios (1-6)
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;

        // 2. Calcular suma
        int suma = dado1 + dado2;

        // 3. Aplicar reglas
        boolean gana = suma == 7 || suma == 11;
        boolean pierde = suma == 2 || suma == 3 || suma == 12;
        String resultado = "CONTINUA";

        if (gana) {
            partida.setEstado(EstadoPartida.FINALIZADA);
            Jugador jugador = partida.getJugador();
            // El jugador gana el doble de la apuesta
            jugador.setSaldo(jugador.getSaldo().add(partida.getApuesta().multiply(BigDecimal.valueOf(2))));
            jugadorRepository.save(jugador);
            partidaRepository.save(partida);
            resultado = "GANA";
        } else if (pierde) {
            partida.setEstado(EstadoPartida.FINALIZADA);
            partidaRepository.save(partida);
            resultado = "PIERDE";
        }

        // 4. Guardar el tiro como ultimo paso
        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);
        tiro.setEsGanador(gana);
        tiro = tiroRepository.save(tiro);

        return TiroResponseDTO.from(tiro, resultado);
    }
}
