package com.example.dunkbet.service;

import com.example.dunkbet.entity.Partido;
import java.util.List;
import java.util.Optional;

public interface PartidoService {
    Partido guardar(Partido partido);
    Optional<Partido> obtenerPorId(Long id);
    List<Partido> listarTodos();
    List<Partido> listarAbiertos();
    void eliminarPorId(Long id);
}