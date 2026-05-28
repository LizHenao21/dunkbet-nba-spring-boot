package com.example.dunkbet.repository;

import com.example.dunkbet.entity.Apuesta;
import com.example.dunkbet.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApuestaRepository extends JpaRepository<Apuesta, Long> {
    
    List<Apuesta> findByPartidoAndEstado(Partido partido, Apuesta.EstadoApuesta estado);
    
    List<Apuesta> findByUsuarioDocumento(Integer documento);

    void deleteByPartidoIdPartido(Long partidoId);
}