package com.example.dunkbet.service;

import com.example.dunkbet.entity.Partido;
import com.example.dunkbet.repository.PartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la capa de servicio para la gestión de partidos en DunkBet.
 * Esta clase contiene la lógica operativa para el ciclo de vida de los encuentros deportivos,
 * interactuando directamente con el repositorio. Garantiza de manera transaccional el almacenamiento 
 * inmediato de datos mediante persistencia síncrona (saveAndFlush) y la eliminación segura de registros, 
 * además de proveer métodos de lectura para búsquedas específicas por ID, listados globales y filtros 
 * de partidos disponibles en estado abierto.
 * * @author Lizeth Henao
 */

@Service
public class PartidoServiceImpl implements PartidoService {

    @Autowired
    private PartidoRepository partidoRepository;

    @Override
    @Transactional
    public Partido guardar(Partido partido) {
        return partidoRepository.saveAndFlush(partido);
    }

    @Override
    public Optional<Partido> obtenerPorId(Long id) {
        return partidoRepository.findById(id);
    }

    @Override
    public List<Partido> listarTodos() {
        return partidoRepository.findAll();
    }

    @Override
    public List<Partido> listarAbiertos() {
        return partidoRepository.findByEstado(Partido.EstadoPartido.abierto);
    }

    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        partidoRepository.deleteById(id);
    }
}