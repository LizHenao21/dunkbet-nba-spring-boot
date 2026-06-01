package com.example.dunkbet.repository;

import com.example.dunkbet.entity.Apuesta;
import com.example.dunkbet.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio de persistencia para la entidad Apuesta en la plataforma DunkBet.
 * Esta interfaz extiende JpaRepository para proporcionar las operaciones CRUD básicas y define 
 * métodos de consulta personalizados (Query Methods). Permite filtrar apuestas por partido y estado, 
 * recuperar el historial de apuestas de un cliente a través de su número de documento, y realizar 
 * la eliminación en cascada de las apuestas vinculadas a un identificador de partido específico.
 * * @author Lizeth Henao
 */

@Repository
public interface ApuestaRepository extends JpaRepository<Apuesta, Long> {
    
    List<Apuesta> findByPartidoAndEstado(Partido partido, Apuesta.EstadoApuesta estado);
    
    List<Apuesta> findByUsuarioDocumento(Integer documento);

    void deleteByPartidoIdPartido(Long partidoId);
}