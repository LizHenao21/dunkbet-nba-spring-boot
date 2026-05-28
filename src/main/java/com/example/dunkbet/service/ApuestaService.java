package com.example.dunkbet.service;

import com.example.dunkbet.entity.Apuesta;
import java.math.BigDecimal;
import java.util.List;

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
