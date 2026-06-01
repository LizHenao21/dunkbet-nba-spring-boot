package com.example.dunkbet.service;

import com.example.dunkbet.entity.Partido;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio que define la lógica de negocio para la gestión de partidos en DunkBet.
 * Establece el contrato para las operaciones esenciales de los encuentros deportivos, permitiendo
 * registrar o actualizar partidos en el sistema, buscar un partido específico por su identificador único,
 * obtener listas completas de los encuentros, filtrar únicamente los partidos disponibles para apostar (abiertos)
 * y eliminar registros de forma definitiva.
 * * @author Lizeth Henao
 */

public interface PartidoService {
    Partido guardar(Partido partido);
    Optional<Partido> obtenerPorId(Long id);
    List<Partido> listarTodos();
    List<Partido> listarAbiertos();
    void eliminarPorId(Long id);
}