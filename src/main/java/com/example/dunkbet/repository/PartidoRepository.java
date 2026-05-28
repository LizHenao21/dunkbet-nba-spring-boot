package com.example.dunkbet.repository;

import com.example.dunkbet.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    List<Partido> findByEstado(Partido.EstadoPartido estado);
}