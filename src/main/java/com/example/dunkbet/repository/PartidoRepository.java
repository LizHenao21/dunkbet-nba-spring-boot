package com.example.dunkbet.repository;

import com.example.dunkbet.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de persistencia para la entidad Partido en la plataforma DunkBet.
 * Esta interfaz extiende JpaRepository para gestionar el acceso a los datos de los encuentros deportivos.
 * Incluye un método de consulta personalizado para filtrar y recuperar el listado de partidos 
 * en función de su estado actual (abiertos o cerrados).
 * * @author Lizeth Henao
 */

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    List<Partido> findByEstado(Partido.EstadoPartido estado);
}