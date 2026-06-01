package com.example.dunkbet.service;

import com.example.dunkbet.entity.Apuesta;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interfaz de servicio que define la lógica de negocio para la gestión de apuestas en DunkBet.
 * Establece el contrato para los métodos encargados de registrar nuevas apuestas (con validación de datos),
 * consultar el historial de transacciones por documento de identidad, liquidar los estados de las apuestas
 * (ganadas o perdidas) tras la finalización de un partido, calcular las ganancias potenciales de forma dinámica
 * y procesar la eliminación o listado general de los registros de juego.
 * * @author Lizeth Henao
 */

public interface ApuestaService {
    Apuesta registrarApuesta(String nombre, String tipoIdentificacion, Integer documento, String email, 
                         Integer edad, String telefono, Long partidoId, String tipoApuestaStr, 
                         String prediccion, BigDecimal monto) throws Exception;

    List<Apuesta> obtenerHistorialPorDocumento(Integer documento) throws Exception;
    void liquidarApuestasPorPartido(Long partidoId, int puntosLocal, int puntosVisitante) throws Exception;
    
    List<Object[]> obtenerGanadoresOPerdedores(Long partidoId, boolean ganadores) throws Exception;
    
    BigDecimal calcularGananciaPotencial(BigDecimal monto, String tipoApuestaStr);

    List<Apuesta> listarTodas();
    void eliminarApuestasPorPartidoId(Long partidoId);
}
