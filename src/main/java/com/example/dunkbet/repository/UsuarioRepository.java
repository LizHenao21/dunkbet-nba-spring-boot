package com.example.dunkbet.repository;

import com.example.dunkbet.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio de persistencia para la entidad Usuario en la plataforma DunkBet.
 * Esta interfaz extiende JpaRepository para administrar el acceso y almacenamiento de los datos de los clientes.
 * Define un método de consulta personalizado que permite buscar y recuperar de forma segura (mediante un contenedor Optional) 
 * a un usuario específico a través de su número de documento de identidad único.
 * * @author Lizeth Henao
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {  
    Optional<Usuario> findByDocumento(Integer documento); 
}