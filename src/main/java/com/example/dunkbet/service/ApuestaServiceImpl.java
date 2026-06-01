package com.example.dunkbet.service;

import com.example.dunkbet.entity.*;
import com.example.dunkbet.repository.ApuestaRepository;
import com.example.dunkbet.repository.PartidoRepository;
import com.example.dunkbet.repository.UsuarioRepository;
import com.example.dunkbet.service.ApuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la capa de servicio para la gestión de apuestas en DunkBet.
 * Esta clase contiene la lógica de negocio detallada del sistema, controlando de manera transaccional 
 * el registro de apuestas (creando dinámicamente al usuario si no existe), el cálculo de cuotas o ganancias 
 * potenciales (multiplicadores de 1.9 para ganador y 1.8 para puntos), la consulta de historiales, 
 * el filtrado analítico de ganadores o perdedores, y la liquidación masiva de estados de juego al cerrarse un partido.
 * * @author Lizeth Henao
 */

@Service
public class ApuestaServiceImpl implements ApuestaService {

    @Autowired
    private ApuestaRepository apuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PartidoRepository partidoRepository;

    @Override
    @Transactional
    public Apuesta registrarApuesta(String nombre, String tipoIdentificacion, Integer documento, String email, 
                                     Integer edad, String telefono, Long partidoId, String tipoApuestaStr, 
                                     String prediccion, BigDecimal monto) throws Exception {

        Usuario usuario = usuarioRepository.findByDocumento(documento)
                .orElseGet(() -> {
                    Usuario nuevo = new Usuario();
                    nuevo.setNombre(nombre);
                    nuevo.setTipoIdentificacion(Usuario.TipoIdentificacion.valueOf(tipoIdentificacion));
                    nuevo.setDocumento(documento);
                    nuevo.setEmail(email);
                    nuevo.setEdad(edad);
                    nuevo.setTelefono(telefono);
                    return usuarioRepository.save(nuevo);
                });

        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new Exception("Partido no encontrado"));
                
        if (partido.getEstado() != Partido.EstadoPartido.abierto) {
            throw new Exception("El partido no está disponible para apostar");
        }

        Apuesta.TipoApuesta tipo;
        try {
            tipo = Apuesta.TipoApuesta.valueOf(tipoApuestaStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new Exception("Tipo de apuesta inválido. Use GANADOR o PUNTOS");
        }

        if (tipo == Apuesta.TipoApuesta.GANADOR) {
            if (!prediccion.equals(partido.getEquipoLocal()) && !prediccion.equals(partido.getEquipoVisitante())) {
                throw new Exception("La predicción debe ser el nombre de uno de los dos equipos");
            }
        } else if (tipo == Apuesta.TipoApuesta.PUNTOS) {
            try {
                Integer.parseInt(prediccion);
            } catch (NumberFormatException e) {
                throw new Exception("Para PUNTOS, la predicción debe ser un número entero");
            }
        }

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("El monto debe ser mayor a cero");
        }

        Apuesta apuesta = new Apuesta();
        apuesta.setUsuario(usuario);
        apuesta.setPartido(partido);
        apuesta.setTipo(tipo);
        apuesta.setPrediccion(prediccion);
        apuesta.setMonto(monto);
        return apuestaRepository.save(apuesta);
    }

    @Override
    @Transactional
    public void liquidarApuestasPorPartido(Long partidoId, int puntosLocal, int puntosVisitante) throws Exception {
        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new Exception("Partido no encontrado"));

        if (partido.getEstado() != Partido.EstadoPartido.abierto) {
            throw new Exception("El partido ya está cerrado");
        }

        if (puntosLocal < 0 || puntosVisitante < 0) {
            throw new Exception("Los puntos no pueden ser negativos");
        }
        if (puntosLocal == puntosVisitante) {
            throw new Exception("En la NBA no hay empates");
        }

        String equipoGanador = (puntosLocal > puntosVisitante) ? partido.getEquipoLocal() : partido.getEquipoVisitante();
        int totalPuntos = puntosLocal + puntosVisitante;

        List<Apuesta> apuestas = apuestaRepository.findByPartidoAndEstado(partido, Apuesta.EstadoApuesta.pendiente);

        for (Apuesta apuesta : apuestas) {
            if (apuesta.getTipo() == Apuesta.TipoApuesta.GANADOR) {
                if (apuesta.getPrediccion().equals(equipoGanador)) {
                    apuesta.setGanancia(apuesta.getMonto().multiply(BigDecimal.valueOf(1.9)));
                    apuesta.setEstado(Apuesta.EstadoApuesta.ganada);
                } else {
                    apuesta.setGanancia(BigDecimal.ZERO);
                    apuesta.setEstado(Apuesta.EstadoApuesta.perdida);
                }
            } else { 
                int prediccionPuntos;
                try {
                    prediccionPuntos = Integer.parseInt(apuesta.getPrediccion());
                } catch (NumberFormatException e) {
                    apuesta.setGanancia(BigDecimal.ZERO);
                    apuesta.setEstado(Apuesta.EstadoApuesta.perdida);
                    continue;
                }
                if (totalPuntos > prediccionPuntos) {
                    apuesta.setGanancia(apuesta.getMonto().multiply(BigDecimal.valueOf(1.8)));
                    apuesta.setEstado(Apuesta.EstadoApuesta.ganada);
                } else {
                    apuesta.setGanancia(BigDecimal.ZERO);
                    apuesta.setEstado(Apuesta.EstadoApuesta.perdida);
                }
            }
            apuestaRepository.save(apuesta);
        }

        partido.setEstado(Partido.EstadoPartido.cerrado);
        partido.setPuntosLocal(puntosLocal);
        partido.setPuntosVisitante(puntosVisitante);
        partido.setEquipoGanador(equipoGanador);
        partidoRepository.save(partido);
    }

    @Override
    public List<Apuesta> obtenerHistorialPorDocumento(Integer documento) throws Exception {
        Usuario usuario = usuarioRepository.findByDocumento(documento)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
        return apuestaRepository.findByUsuarioDocumento(documento);
    }

    @Override
    public List<Object[]> obtenerGanadoresOPerdedores(Long partidoId, boolean ganadores) throws Exception {
        Partido partido = partidoRepository.findById(partidoId)
                .orElseThrow(() -> new Exception("Partido no encontrado"));

        if (partido.getEstado() != Partido.EstadoPartido.cerrado) {
            throw new Exception("El partido aún no está cerrado");
        }

        Apuesta.EstadoApuesta estadoBuscado = ganadores ? Apuesta.EstadoApuesta.ganada : Apuesta.EstadoApuesta.perdida;

        List<Apuesta> apuestas = apuestaRepository.findByPartidoAndEstado(partido, estadoBuscado)
                .stream()
                .filter(a -> a.getTipo() == Apuesta.TipoApuesta.GANADOR)
                .collect(Collectors.toList());

        return apuestas.stream()
                .map(a -> new Object[]{
                        a.getUsuario().getIdUsuario(),
                        a.getUsuario().getNombre(),
                        a.getUsuario().getTelefono(),
                        a.getUsuario().getEmail(),
                        a.getMonto(),
                        a.getGanancia()
                })
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal calcularGananciaPotencial(BigDecimal monto, String tipoApuestaStr) {
        if (tipoApuestaStr.equalsIgnoreCase("GANADOR")) {
            return monto.multiply(BigDecimal.valueOf(1.9));
        } else if (tipoApuestaStr.equalsIgnoreCase("PUNTOS")) {
            return monto.multiply(BigDecimal.valueOf(1.8));
        } else {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public List<Apuesta> listarTodas() {
        throw new UnsupportedOperationException("Unimplemented method 'listarTodas'");
    }

    @Override
    @Transactional
    public void eliminarApuestasPorPartidoId(Long partidoId) {
        apuestaRepository.deleteByPartidoIdPartido(partidoId);
}
}