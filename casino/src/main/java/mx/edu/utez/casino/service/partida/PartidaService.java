package mx.edu.utez.casino.service.partida;

import mx.edu.utez.casino.controller.dto.partida.IniciarPartidaRequestDTO;
import mx.edu.utez.casino.controller.dto.partida.PartidaResponseDTO;
import mx.edu.utez.casino.exception.customExceptions.BadRequestException;
import mx.edu.utez.casino.exception.customExceptions.NotFoundException;
import mx.edu.utez.casino.model.jugador.Jugador;
import mx.edu.utez.casino.model.partida.EstadoPartida;
import mx.edu.utez.casino.model.partida.Partida;
import mx.edu.utez.casino.repository.jugador.JugadorRepository;
import mx.edu.utez.casino.repository.partida.PartidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;

    public PartidaService(PartidaRepository partidaRepository, JugadorRepository jugadorRepository) {
        this.partidaRepository = partidaRepository;
        this.jugadorRepository = jugadorRepository;
    }

    // iniciarPartida
    @Transactional
    public PartidaResponseDTO iniciarPartida(IniciarPartidaRequestDTO dto) {
        Jugador jugador = jugadorRepository.findById(dto.getJugadorId())
                .orElseThrow(() -> new NotFoundException("El jugador con id " + dto.getJugadorId() + " no existe"));

        if (!jugador.isActivo()) {
            throw new BadRequestException("El jugador no está activo");
        }
        if (jugador.getSaldo().compareTo(dto.getApuesta()) < 0) {
            throw new BadRequestException("Saldo insuficiente para realizar la apuesta");
        }

        // Descontar apuesta del saldo
        jugador.setSaldo(jugador.getSaldo().subtract(dto.getApuesta()));
        jugadorRepository.save(jugador);

        // Crear partida en estado EN_JUEGO
        Partida partida = new Partida();
        partida.setFecha(LocalDateTime.now());
        partida.setEstado(EstadoPartida.EN_JUEGO);
        partida.setJugador(jugador);
        partida.setApuesta(dto.getApuesta());
        return PartidaResponseDTO.from(partidaRepository.save(partida));
    }

    // finalizarPartidaManual
    @Transactional
    public PartidaResponseDTO finalizarPartidaManual(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new NotFoundException("La partida con id " + partidaId + " no existe"));

        if (partida.getEstado() == EstadoPartida.FINALIZADA) {
            throw new BadRequestException("La partida ya está finalizada");
        }
        partida.setEstado(EstadoPartida.FINALIZADA);
        return PartidaResponseDTO.from(partidaRepository.save(partida));
    }

    // obtenerHistorialPartida
    @Transactional(readOnly = true)
    public List<PartidaResponseDTO> obtenerHistorialPartida(Long jugadorId) {
        if (!jugadorRepository.existsById(jugadorId)) {
            throw new NotFoundException("El jugador con id " + jugadorId + " no existe");
        }
        return partidaRepository.findByJugadorIdOrderByFechaDesc(jugadorId).stream()
                .map(PartidaResponseDTO::from)
                .toList();
    }
}
